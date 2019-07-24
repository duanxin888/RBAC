package com.duanxin.service;

import com.duanxin.beans.PageQuery;
import com.duanxin.beans.PageResult;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysAclMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysAcl;
import com.duanxin.params.AclParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysAclService
 * @Description 权限Service层类
 * @date 2019/7/21 9:47
 */
@Service
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysLogService sysLogService;

    /**
     * @description 分页查询权限信息
     * @param [aclModuleId, pageQuery]
     * @date 2019/7/21 10:30
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    @Transactional
    public PageResult<SysAcl> getAclListByAclModuleId(Integer aclModuleId, PageQuery pageQuery) {
        // 进行校验
        BeanValidator.check(pageQuery);

        List<SysAcl> sysAcls = sysAclMapper.getAclListByAclModuleId(aclModuleId, pageQuery);
        int total = sysAclMapper.countAclByAclModuleId(aclModuleId);
        if (CollectionUtils.isNotEmpty(sysAcls)) {
            return PageResult.<SysAcl>builder().data(sysAcls).total(total).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    /**
     * @description 添加权限信息
     * @param [param]
     * @date 2019/7/21 9:48
     **/
    public void save(AclParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkNameExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("同意权限模块下不予许出现同名权限");
        }

        SysAcl sysAcl = SysAcl.builder().name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).seq(param.getSeq()).build();
        sysAcl.setCode(generateCode());
        sysAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysAcl.setOperateTime(new Date());
        sysAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));


        sysAclMapper.insertSelective(sysAcl);
        sysLogService.saveAclLog(null, sysAcl);
    }

    /**
     * @description 更新权限信息
     * @param [param]
     * @date 2019/7/21 9:49
     **/
    public void update(AclParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkNameExist(param.getAclModuleId(), param.getName(), param.getId())) {
            throw new ParamException("同意权限模块下不予许出现同名权限");
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限不存在");

        SysAcl after = SysAcl.builder().id(param.getId()).name(param.getName()).aclModuleId(param.getAclModuleId())
                .url(param.getUrl()).type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).seq(param.getSeq()).build();
        after.setCode(generateCode());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());

        sysAclMapper.updateByPrimaryKeySelective(after);

        sysLogService.saveAclLog(before, after);
    }

    /**
     * @description 校验同模块下是否出现同名权限
     * @param [aclModuleId, name, id]
     * @date 2019/7/21 9:54
     * @return boolean
     **/
    private boolean checkNameExist(Integer aclModuleId, String name, Integer id) {
        return sysAclMapper.countAclByName(aclModuleId, name, id) > 0;
    }

    /**
     * @description 随机生成code
     * @param []
     * @date 2019/7/21 10:18
     * @return java.lang.String
     **/
    private String generateCode(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        return simpleDateFormat.format(new Date()) + "_" + (int)(Math.random()*100);
    }
}
