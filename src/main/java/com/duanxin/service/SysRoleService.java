package com.duanxin.service;

import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.*;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysAcl;
import com.duanxin.model.SysRole;
import com.duanxin.model.SysUser;
import com.duanxin.params.RoleParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysRoleService
 * @Description 角色Service层类
 * @date 2019/7/21 11:35
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;

    /**
     * @description 根据角色集合获取用户集合
     * @param [roleList]
     * @date 2019/7/23 9:30
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        // 进行校验
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }

        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    /**
     * @description 根据权限id获取角色集合
     * @param [aclId]
     * @date 2019/7/23 9:21
     * @return java.util.List<com.duanxin.model.SysRole>
     **/
    public List<SysRole> getRoleListByAclId(Integer aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * @description 根据用户id获取角色
     * @param [userId]
     * @date 2019/7/23 8:57
     * @return java.util.List<com.duanxin.model.SysRole>
     **/
    public List<SysRole> getByUserId(Integer userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    /**
     * @description 添加角色信息
     * @param [param]
     * @date 2019/7/21 11:43
     **/
    public void save(RoleParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkNameExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }

        SysRole sysRole = SysRole.builder().name(param.getName())
                .type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).build();
        sysRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysRole.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysRole.setOperateTime(new Date());

        sysRoleMapper.insertSelective(sysRole);

        sysLogService.saveRoleLog(null, sysRole);
    }

    /**
     * @description 更新角色信息
     * @param [param]
     * @date 2019/7/21 11:44
     **/
    public void update(RoleParam param) {
        // 进行校验
        BeanValidator.check(param);
        if (checkNameExist(param.getName(), param.getId())) {
            throw new ParamException("角色名称已经存在");
        }

        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的角色不存在");

        SysRole after = SysRole.builder().id(param.getId()).name(param.getName())
                .type(param.getType()).status(param.getStatus())
                .remark(param.getRemark()).build();
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        after.setOperator(RequestHolder.getCurrentUser().getUsername());

        sysRoleMapper.updateByPrimaryKeySelective(after);

        sysLogService.saveRoleLog(before, after);
    }

    /**
     * @description 查询所有角色
     * @param []
     * @date 2019/7/21 15:45
     * @return java.util.List<com.duanxin.model.SysRole>
     **/
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    /**
     * @description 校验
     * @param [name, id]
     * @date 2019/7/21 15:44
     * @return boolean
     **/
    private boolean checkNameExist(String name, Integer id) {
        return sysRoleMapper.countRoleByName(name, id) > 0;
    }
}
