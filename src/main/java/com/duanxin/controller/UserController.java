package com.duanxin.controller;


import com.duanxin.model.SysUser;
import com.duanxin.service.SysUserService;
import com.duanxin.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName UserController
 * @Description 用户controller层登录相关操作
 * @date 2019/7/19 18:23
 */
@Controller
public class UserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * @description 用户退出
     * @param [req, resp]
     * @date 2019/7/19 20:04
     **/
    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().invalidate();
        String path = "signin.jsp";
        resp.sendRedirect(path);
    }

    /**
     * @description 用户登入操作
     * @param [req, resp]
     * @date 2019/7/19 18:29
     **/
    @RequestMapping("/login.page")
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String usernmae = req.getParameter("username");
        String password = req.getParameter("password");

        SysUser sysUser = sysUserService.findByKeyword(usernmae);
        String errorMsg = "";
        String ret = req.getParameter("ret");

        if (StringUtils.isBlank(usernmae)) {
            // username为空时
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            // password为空时
            errorMsg = "密码不可以为空";
        } else if (sysUser == null) {
            // 获取的sysUser为null时
            errorMsg = "查询不到指定用户";
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            // 数据库查询的password和页面传回来的password不一致时
            errorMsg = "用户名或密码不正确";
        } else if (sysUser.getStatus() != 1) {
            // 用户状态不正常时
            errorMsg = "用户状态被冻结，请联系管理员";
        } else {
            // login success
            req.getSession().setAttribute("user", sysUser);
            if (StringUtils.isNotBlank(ret)) {
                resp.sendRedirect(ret);
            } else {
                // todo:/admin/index.page
                resp.sendRedirect("/admin/index.page");
            }
        }

        // 当出现error时
        req.setAttribute("errorMsg", errorMsg);
        req.setAttribute("username", usernmae);
        if (StringUtils.isNotBlank(ret)) {
            req.setAttribute("ret", ret);
        }
        String path = "singin.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
