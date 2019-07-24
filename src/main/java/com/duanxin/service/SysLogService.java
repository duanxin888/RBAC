package com.duanxin.service;

import com.duanxin.beans.LogType;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.SysLogMapper;
import com.duanxin.model.*;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.JsonMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysLogService
 * @Description Service层日志类
 * @date 2019/7/24 11:24
 */
@Service
public class SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    /**
     * @description 保存部门更新日志
     * @param [before, after]
     * @date 2019/7/24 11:31
     **/
    public void saveDeptLog(SysDept before, SysDept after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_DEPT);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(0);

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * @description 保存用户更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    public void saveUserLog(SysUser before, SysUser after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_USER);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(0);

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * @description 保存权限模块更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setStatus(0);
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * @description 保存权限更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setStatus(0);
        sysLog.setOperateTime(new Date());

        sysLogMapper.insertSelective(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(0);

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * @description 保存角色权限更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    public void saveRoleAclLog(Integer roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperateTime(new Date());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setStatus(0);

        sysLogMapper.insertSelective(sysLog);
    }

    /**
     * @description 保存角色用户更新日志
     * @param [before, after]
     * @date 2019/7/24 11:32
     **/
    public void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
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
