package com.duanxin.controller;

import com.duanxin.beans.PageQuery;
import com.duanxin.beans.PageResult;
import com.duanxin.commons.JsonData;
import com.duanxin.model.SysAcl;
import com.duanxin.model.SysRole;
import com.duanxin.params.AclParam;
import com.duanxin.service.SysAclService;
import com.duanxin.service.SysRoleService;
import com.duanxin.service.SysTreeService;
import com.duanxin.service.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysAclController
 * @Description 权限Controller类
 * @date 2019/7/20 11:17
 */
@Controller
@RequestMapping("/sys/acl")
public class SysAclController {

    @Resource
    private SysAclService sysAclService;
    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") Integer aclId) {
        Map<String, Object> map = Maps.newHashMap();
        List<SysRole> sysRoleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("roles", sysRoleList);
        map.put("users", sysRoleService.getUserListByRoleList(sysRoleList));
        return JsonData.success(map);
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData save(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData update(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery){
        PageResult<SysAcl> result = sysAclService.getAclListByAclModuleId(aclModuleId, pageQuery);
        return JsonData.success(result);
    }

}
