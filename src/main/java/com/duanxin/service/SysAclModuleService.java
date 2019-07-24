package com.duanxin.service;

import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysAclMapper;
import com.duanxin.dao.SysAclModuleMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysAclModule;
import com.duanxin.params.AclModuleParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysAclModuleService
 * @Description 权限模块service层类
 * @date 2019/7/20 11:37
 */
@Service
public class SysAclModuleService {
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;

    public void deleteById(Integer aclModuleId) {
        // 进行校验是否存在
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(sysAclModule, "待删除的权限模块不存在，无法删除");
        // 校验是否有子模块存在
        if (sysAclModuleMapper.countByParentId(aclModuleId) > 0) {
            throw new ParamException("待删除的权限模块下有子模块，无法删除");
        }
        // 校验是否有权限存在
        if (sysAclMapper.countAclByAclModuleId(aclModuleId) > 0) {
            throw new ParamException("待删除的权限模块下有权限，无法删除");
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }

    /**
     * @description 更新权限模块信息
     * @param [param]
     * @date 2019/7/20 14:42
     **/
    public void update(AclModuleParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getParentId())) {
            throw new ParamException("同一层下出现了相同名称的权限模块");
        }

        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在");

        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq()).status(param.getStatus())
                .remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());

        updateWithChild(before, after);
    }

    /**
     * @description 保存权限模块信息
     * @param [param]
     * @date 2019/7/20 11:38
     **/
    public void save(AclModuleParam param){
        // 进行校验
        BeanValidator.check(param);
        if (checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层下出现了相同名称的权限模块");
        }

        // 获取SysAclModule对象
        SysAclModule sysAclModule = SysAclModule.builder().name(param.getName())
                .parentId(param.getParentId()).seq(param.getSeq())
                .status(param.getStatus()).remark(param.getRemark()).build();
        sysAclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclModule.setOperateTime(new Date());

        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    private String getLevel(Integer id) {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(id);
        if (sysAclModule == null) {
            return null;
        }
        return sysAclModule.getLevel();
    }

    private boolean checkExist(Integer parentId, String name, Integer id){
        return sysAclModuleMapper.countAclModelByName(parentId, name, id) > 0;
    }

    @Transactional(rollbackFor = ParamException.class)
    public void updateWithChild(SysAclModule before, SysAclModule after){
        String beforeLevelPefix = before.getLevel();
        String afterLevelPefix = after.getLevel();

        // 进行判断level是否一致
        if (!StringUtils.equals(beforeLevelPefix, afterLevelPefix)) {
            List<SysAclModule> childList = sysAclModuleMapper.getChildListByLevel(beforeLevelPefix);
            if (CollectionUtils.isNotEmpty(childList)) {
                for (SysAclModule module : childList) {
                    String level = module.getLevel();
                    if (level.startsWith(beforeLevelPefix)) {
                        level = StringUtils.join(afterLevelPefix, module.getLevel().substring(beforeLevelPefix.length()));
                        module.setLevel(level);
                    }
                }
            }
            // 批量更新
            sysAclModuleMapper.batchUpdateLevel(childList);
        }

        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }
}
