package com.duanxin.controller;

import com.duanxin.commons.JsonData;
import com.duanxin.dto.DeptLevelDto;
import com.duanxin.params.DeptParam;
import com.duanxin.service.SysDeptService;
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
 * @ClassName SysDeptController
 * @Description 部门controller层类
 * @date 2019/7/17 17:53
 */
@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param) {
        sysDeptService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree() {
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();

        return JsonData.success(dtoList);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") Integer deptId) {
        sysDeptService.deleteById(deptId);
        return JsonData.success();
    }
}
