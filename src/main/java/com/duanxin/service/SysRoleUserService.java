package com.duanxin.service;

import com.duanxin.beans.LogType;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysLogMapper;
import com.duanxin.dao.SysRoleUserMapper;
import com.duanxin.dao.SysUserMapper;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.SysLogWithBLOBs;
import com.duanxin.model.SysRoleUser;
import com.duanxin.model.SysUser;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.JsonMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysRoleAclService
 * @Description Service层角色用户类
 * @date 2019/7/22 15:40
 */
@Service
public class SysRoleUserService {

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * @description 添加用户角色数据
     * @param [roleId, userIdLists]
     * @date 2019/7/22 17:57
     **/
    public void changeUsers(Integer roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        // 进行判断
        if (userIdList.size() == originUserIdList.size()) {
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) {
                return ;
            }
        }

        updateRoleUser(roleId, userIdList);

        saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    /**
     * @description 更新角色用户数据
     * @param [roleId, userIdList]
     * @date 2019/7/22 18:03
     **/
    @Transactional(rollbackFor = ParamException.class)
    public void updateRoleUser(Integer roleId, List<Integer> userIdList) {
        // 删除之前的数据
        sysRoleUserMapper.deleteByRoleId(roleId);
        // 进行为空判断
        if (CollectionUtils.isEmpty(userIdList)) {
            return ;
        }

        List<SysRoleUser> sysRoleUsers = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().userId(userId)
                    .roleId(roleId).operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()))
                    .operateTime(new Date()).operator(RequestHolder.getCurrentUser().getUsername())
                    .build();
            sysRoleUsers.add(roleUser);
        }

        sysRoleUserMapper.batchInsert(sysRoleUsers);
    }

    /**
     * @description 获取用户集合
     * @param [roleId]
     * @date 2019/7/22 16:57
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    public List<SysUser> getUserListByRoleId(Integer roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        // 进行判空操作
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        return sysUserMapper.getByIdList(userIdList);
    }

    /**
     * @description 保存角色用户更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    private void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setOperateTime(new Date());
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setStatus(0);

        sysLogMapper.insertSelective(sysLog);
    }
}
