package com.duanxin.controller;

import com.duanxin.commons.JsonData;
import com.duanxin.dto.AclModuleLevelDto;
import com.duanxin.model.SysAclModule;
import com.duanxin.params.AclModuleParam;
import com.duanxin.params.DeptParam;
import com.duanxin.service.SysAclModuleService;
import com.duanxin.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysAclModuleController
 * @Description 权限模块Controller层类
 * @date 2019/7/20 11:16
 */
@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<AclModuleLevelDto> tree = sysTreeService.aclModuleTree();
        return JsonData.success(tree);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") Integer aclModuleId) {
        sysAclModuleService.deleteById(aclModuleId);
        return JsonData.success();
    }
}
