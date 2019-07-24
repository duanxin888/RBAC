package com.duanxin.controller;

import com.duanxin.service.SysLogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
