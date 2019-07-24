package com.duanxin.controller;

import com.duanxin.beans.PageQuery;
import com.duanxin.beans.PageResult;
import com.duanxin.commons.JsonData;
import com.duanxin.model.SysUser;
import com.duanxin.params.UserParam;
import com.duanxin.service.SysRoleService;
import com.duanxin.service.SysTreeService;
import com.duanxin.service.SysUserService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysUserController
 * @Description 用户controller类
 * @date 2019/7/19 17:08
 */
@RequestMapping("/sys/user")
@Controller
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysTreeService sysTreeService;
    @Resource
    private SysRoleService sysRoleService;


    @RequestMapping("/noAuth.page")
    public ModelAndView noAuth() {
        return new ModelAndView("noAuth");
    }

    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("userId") Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysTreeService.userAclTree(userId));
        map.put("roles", sysRoleService.getByUserId(userId));
        return JsonData.success(map);
    }

    /**
     * @description 保存用户信息
     * @param [param]
     * @date 2019/7/20 8:19
     * @return com.duanxin.commons.JsonData
     **/
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveUser(UserParam param) {
        sysUserService.save(param);
        return JsonData.success();
    }

    /**
     * @description 更新用户信息
     * @param [param]
     * @date 2019/7/20 8:19
     * @return com.duanxin.commons.JsonData
     **/
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateUser(UserParam param) {
        sysUserService.update(param);
        return JsonData.success();
    }

    /**
     * @description 用于处理用户分页
     * @param [deptId, page]
     * @date 2019/7/20 7:33
     * @return com.duanxin.commons.JsonData
     **/
    @RequestMapping("page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") Integer deptId, PageQuery page){
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, page);
        return JsonData.success(result);
    }

}
