package com.duanxin.controller;

import com.duanxin.beans.PageQuery;
import com.duanxin.commons.JsonData;
import com.duanxin.params.SearchLogParam;
import com.duanxin.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SysLogController
 * @Description Controller层日志类
 * @date 2019/7/24 11:23
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {


    @Resource
    private SysLogService sysLogService;

    @RequestMapping("/log.page")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page) {
        return JsonData.success(sysLogService.searchPageList(param, page));
    }

    @RequestMapping("/recover.json")
    @ResponseBody
    public JsonData recover(@RequestParam("id") Integer logId){
        sysLogService.recover(logId);
        return JsonData.success();
    }

}
