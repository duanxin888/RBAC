package com.duanxin.service;

import com.duanxin.beans.LogType;
import com.duanxin.beans.PageQuery;
import com.duanxin.beans.PageResult;
import com.duanxin.commons.RequestHolder;
import com.duanxin.dao.*;
import com.duanxin.dto.SearchLogDto;
import com.duanxin.exceptions.ParamException;
import com.duanxin.model.*;
import com.duanxin.params.SearchLogParam;
import com.duanxin.utils.BeanValidator;
import com.duanxin.utils.IpUtil;
import com.duanxin.utils.JsonMapper;
import com.duanxin.utils.StringUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleAclService sysRoleAclService;
    @Resource
    private SysRoleUserService sysRoleUserService;

    /**
     * @description 还原日志记录
     * @param [logId]
     * @date 2019/7/24 18:27
     **/
    public void recover(Integer logId) {
        SysLogWithBLOBs sysLog = sysLogMapper.selectByPrimaryKey(logId);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()) {
            case LogType.TYPE_DEPT :
                SysDept beforeDept = sysDeptMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeDept, "待还原的部门已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除部门不进行还原");
                }
                SysDept afterDept = (SysDept) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysDept>(){});
                afterDept.setOperateTime(new Date());
                afterDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterDept.setOperator(RequestHolder.getCurrentUser().getUsername());
                sysDeptMapper.updateByPrimaryKeySelective(afterDept);
                saveDeptLog(beforeDept, afterDept);
                break;
            case LogType.TYPE_USER :
                SysUser beforeUser = sysUserMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeUser, "待还原的用户已经不存在");
                if (StringUtils.isBlank(sysLog.getOldValue()) || StringUtils.isBlank(sysLog.getNewValue())) {
                    throw new ParamException("新增和删除用户不进行更新");
                }
                SysUser afterUser = (SysUser) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysUser>() {
                });
                afterUser.setOperateTime(new Date());
                afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
                sysUserMapper.updateByPrimaryKeySelective(afterUser);
                saveUserLog(beforeUser, afterUser);
                break;
            case LogType.TYPE_ACL_MODULE :
                SysAclModule beforeAclModule = sysAclModuleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除权限模块不进行还原");
                }
                SysAclModule afterAclModule = (SysAclModule) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });
                afterAclModule.setOperateTime(new Date());
                afterAclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
                sysAclModuleMapper.updateByPrimaryKeySelective(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            case LogType.TYPE_ACL :
                SysAcl beforeAcl = sysAclMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的权限已经不存在了");
                if (StringUtils.isBlank(sysLog.getOldValue()) || StringUtils.isBlank(sysLog.getNewValue())) {
                    throw new ParamException("新增和删除权限不进行还原");
                }
                SysAcl afterAcl = (SysAcl) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });
                afterAcl.setOperateTime(new Date());
                afterAcl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperator(RequestHolder.getCurrentUser().getUsername());
                sysAclMapper.updateByPrimaryKeySelective(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            case LogType.TYPE_ROLE :
                SysRole beforeRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的角色已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除角色不进行还原");
                }
                SysRole afterRole = (SysRole) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });
                afterRole.setOperateTime(new Date());
                afterRole.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperator(RequestHolder.getCurrentUser().getUsername());
                sysRoleMapper.updateByPrimaryKeySelective(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            case LogType.TYPE_ROLE_ACL :
                SysRole aclRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole, "角色已经不存在了");
                if (StringUtils.isBlank(sysLog.getOldValue()) || StringUtils.isBlank(sysLog.getNewValue())) {
                    throw new ParamException("更新和删除角色权限不进行更新");
                }
                List<Integer> afterAclIdList =
                        (List<Integer>) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {});
                sysRoleAclService.changeRoleAcls(sysLog.getTargetId(), afterAclIdList);
                break;
            case LogType.TYPE_ROLE_USER :
                SysRole userRole = sysRoleMapper.selectByPrimaryKey(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole, "角色已经不存在了");
                List<Integer> afterUserIdList =
                        (List<Integer>) JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<List<Integer>>() {});
                sysRoleUserService.changeUsers(sysLog.getTargetId(), afterUserIdList);
                break;
            default : break;
        }
    }

    /**
     * @description 分页查询日志数据
     * @param [param, page]
     * @date 2019/7/24 17:23
     * @return com.duanxin.beans.PageResult<com.duanxin.model.SysLog>
     **/
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page) {
        // 进行校验
        BeanValidator.check(page);
        SearchLogDto dto = new SearchLogDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dto.setType(param.getType());
            if (StringUtils.isNotBlank(param.getBeforeSeg())) {
                dto.setBeforeSeg("%" + param.getBeforeSeg() + "%");
            }
            if (StringUtils.isNotBlank(param.getAfterSeg())) {
                dto.setAfterSeg("%" + param.getAfterSeg() + "%");
            }
            if (StringUtils.isNotBlank(param.getOperator())) {
                dto.setOperator("%" + param.getOperator() + "%");
            }
            if (StringUtils.isNotBlank(param.getFromTime())) {
                dto.setFromTime(sdf.parse(param.getFromTime()));
            }
            if (StringUtils.isNotBlank(param.getToTime())) {
                dto.setToTime(sdf.parse(param.getToTime()));
            }
        } catch (Exception e) {
            throw new ParamException("传入的日期格式有误，正确格式为：yyyy-MM-dd HH:mm:ss");
        }
        int count = sysLogMapper.countBySearchDto(dto);
        if (count > 0) {
            List<SysLogWithBLOBs> logList = sysLogMapper.getPageListBySearchDto(dto, page);
            return PageResult.<SysLogWithBLOBs>builder().total(count).data(logList).build();
        }
        return PageResult.<SysLogWithBLOBs>builder().build();
    }

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

    /**
     * @description 保存角色更新日志
     * @param [before, after]
     * @date 2019/7/24 17:22
     **/
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
}
