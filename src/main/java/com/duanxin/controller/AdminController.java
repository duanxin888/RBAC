package com.duanxin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName AdminController
 * @Description 登入成功操作
 * @date 2019/7/19 18:48
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/index.page")
    public ModelAndView index(){
        return new ModelAndView("admin");
    }
}
