package com.duanxin.filters;

import com.duanxin.commons.RequestHolder;
import com.duanxin.model.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName LoginFilter
 * @Description 登入过滤器：将请求对象放入线程池中
 * @date 2019/7/20 10:21
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 进行强转对象
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 获取登入用户对象
        SysUser sysUser = (SysUser) req.getSession().getAttribute("user");
        // 进行判断
        if (sysUser == null) {
            // 登入失败，跳转登入界面
            String path = "/signin.jsp";
            resp.sendRedirect(path);
            return ;
        }
        // 登入成功，对象存入线程池
        RequestHolder.set(sysUser);
        RequestHolder.set(req);
        chain.doFilter(request, response);
        return ;
    }

    @Override
    public void destroy() {

    }
}
