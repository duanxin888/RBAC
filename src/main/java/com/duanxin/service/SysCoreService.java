package com.duanxin.service;

import com.duanxin.beans.CacheKeyConstants;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysAclMapper;
import com.duanxin.dao.SysRoleAclMapper;
import com.duanxin.dao.SysRoleUserMapper;
import com.duanxin.model.SysAcl;
import com.duanxin.model.SysUser;
import com.duanxin.utils.JsonMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysCoreService
 * @Description 业务层权限获取类
 * @date 2019/7/22 7:12
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysCacheService sysCacheService;

    /**
     * @description 判断该用户是否有权访问该url
     * @param [url]
     * @date 2019/7/23 10:23
     * @return boolean
     **/
    public boolean hasUrlAcl(String url) {
        // 判断是否是超级管理员
        if (isSuperAdmin()) {
            return true;
        }
        // 获取该url所属的权限集合
        List<SysAcl> sysAclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(sysAclList)) {
            return true;
        }
        // 获取该用户的访问权限
        List<SysAcl> userAclList = getCurrentUserAclListFromChache();
        Set<Integer> userAclIdSet =
                userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        // 对该url的所有访问权限点是否有效进行标记
        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，就有访问权限
        for (SysAcl sysAcl : sysAclList) {
            // 判断一个用户是否具有某个权限点得访问权限
            if (sysAcl == null || sysAcl.getStatus() != 1) {
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(sysAcl.getId())) {
                return true;
            }
        }

        if (!hasValidAcl) {
            return true;
        }
        return false;
    }

    /**
     * @description 获取当前用户已分配的权限
     * @param []
     * @date 2019/7/22 7:16
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    public List<SysAcl> getCurrentUserAclList() {
        // 获取当前角色id
        Integer id = RequestHolder.getCurrentUser().getId();
        return getUserAclList(id);
    }

    /**
     * @description 根据角色id获取角色已分配的权限
     * @param [roleId]
     * @date 2019/7/22 7:17
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    public List<SysAcl> getRoleAclList(Integer roleId) {
        List<Integer> aclIdList =
                sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(aclIdList);
    }

    /**
     * @description 根据用户id获取已分配的权限
     * @param [id]
     * @date 2019/7/22 7:24
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    public List<SysAcl> getUserAclList(Integer userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        // 获取角色id集合
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        // 获取权限id集合
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        // 获取权限对象集合
        return sysAclMapper.getByIdList(aclIdList);
    }

    private boolean isSuperAdmin(){
        // 获取当前用户
        SysUser sysUser = RequestHolder.getCurrentUser();
        // 进行判断
        if (sysUser.getUsername().contains("root")) {
            return true;
        }
        return false;
    }

    /**
     * @description 从缓存中获取当前用户权限集合
     * @param []
     * @date 2019/7/24 9:56
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    public List<SysAcl> getCurrentUserAclListFromChache() {
        int userId = RequestHolder.getCurrentUser().getId();
        // 从缓存中取数据
        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS,
                String.valueOf(userId));
        // 判断缓存中的数据是否有效
        if (StringUtils.isBlank(cacheValue)) {
            // 取数据库中的数据
            List<SysAcl> aclList = getCurrentUserAclList();
            // 存入缓存中
            sysCacheService.saveCache(JsonMapper.obj2String(aclList), 1000,
                    CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            return aclList;
        }
        // 有效返回数据
        return (List<SysAcl>) JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });
    }
}
