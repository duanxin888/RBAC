package com.duanxin.service;

import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysDeptMapper;
import com.duanxin.dao.SysUserMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysDept;
import com.duanxin.params.DeptParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysDeptService
 * @Description TODO
 * @date 2019/7/17 17:55
 */
@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * @description 根据id删除数据
     * @param [deptId]
     * @date 2019/7/23 7:44
     **/
    public void deleteById(Integer deptId) {
        // 进行校验
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除的部门不存在，无法删除");
        // 校验是否有子部门存在
        if (sysDeptMapper.countByParentId(deptId) > 0) {
            throw new ParamException("待删除的部门有子部门，无法删除");
        }
        // 校验是否有用户存在
        if (sysUserMapper.countByDeptId(deptId) > 0) {
            throw new ParamException("待删除的部门有用户，无法删除");
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    /**
     * @description 保存部门数据
     * @param [param] 保存的部门参数
     * @date 2019/7/17 17:58
     **/
    public void save(DeptParam param){
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层下出现相同名称的部门");
        }
        // 获取SysDept对象
        SysDept sysDept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperateTime(new Date());
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));

        sysDeptMapper.insertSelective(sysDept);
    }

    /**
     * @description 校验是否存在相同的部门名称
     * @param [parentId, deptName, deptId] 上级部门id，部门名称，部门id
     * @date 2019/7/17 18:00
     * @return boolean
     **/
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    private String getLevel(Integer id){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(id);
        if (dept == null) {
           return null;
        }
        return dept.getLevel();
    }

    /**
     * @description 更新部门数据
     * @param [param] 更新的部门数据
     * @date 2019/7/18 8:41
     **/
    public void update(DeptParam param) {
        // 进行校验信息
        BeanValidator.check(param);
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一级下出现相同名称的部门");
        }

        // 进行更新操作
        SysDept after = SysDept.builder().id(param.getId()).name(param.getName())
                .parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());

        // 更新数据
        updateWithChild(before, after);
    }

    /**
     * @description 更新和子部门的关系
     * @param [before, after] 更新前，更新后
     * @date 2019/7/18 8:50
     **/
    @Transactional(rollbackFor = ParamException.class)
    public void updateWithChild(SysDept before, SysDept after) {
        // 获取level前缀
        String afterLevelPrefix = after.getLevel();
        String beforeLevelPrefix = before.getLevel();

        // 进行判断
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysDept> deptList = sysDeptMapper.getChildListByLevel(before.getLevel());
            // 判断不为空时进行遍历
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.startsWith(beforeLevelPrefix)) {
                        level = afterLevelPrefix + level.substring(beforeLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                // 进行批量更新
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        // 更新当前部门信息
        sysDeptMapper.updateByPrimaryKeySelective(after);
    }
}
