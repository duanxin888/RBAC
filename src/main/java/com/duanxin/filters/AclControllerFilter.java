package com.duanxin.filters;

import com.duanxin.commons.ApplicationContextHelper;
import com.duanxin.commons.JsonData;
import com.duanxin.commons.RequestHolder;
import com.duanxin.model.SysUser;
import com.duanxin.service.SysCoreService;
import com.duanxin.utils.JsonMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName AclControllerFilter
 * @Description 权限管理过滤器
 * @date 2019/7/23 10:04
 */
@Slf4j
public class AclControllerFilter implements Filter {

    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private static final String noAuthUrl = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 从配置文件获取不拦截的url
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        // 将urls转化为list集合
        List<String> exclusionUrlList =
                Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        // 转化为set集合
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();
        Map requestMap = req.getParameterMap();

        // 对白名单url放行
        if (exclusionUrlSet.contains(path)) {
            log.info("someone visit {}, but no login, parameter:{}", path, JsonMapper.obj2String(requestMap));
            chain.doFilter(request, response);
            return ;
        }

        // 对非白名单url进行处理
        // 判断用户是否登入
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser == null) {
            noAuth(req, resp);
            return ;
        }

        // 判断这个用户是否有权访问某个url
        SysCoreService sysCoreService = ApplicationContextHelper.popBean(SysCoreService.class);
        if (!sysCoreService.hasUrlAcl(path)) {
            log.info("{} visit {},but no login, parameter:{}",
                    JsonMapper.obj2String(sysUser),
                    path, JsonMapper.obj2String(requestMap));
            noAuth(req, resp);
            return ;
        }

        // 过了所有拦截，放行
        chain.doFilter(request, response);
    }

    /**
     * @description 无权访问操作
     * @param []
     * @date 2019/7/23 10:17
     **/
    private void noAuth(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String servletPath = req.getServletPath();
        if (servletPath.endsWith(".json")) {
            JsonData jsonData = JsonData.fail("没有访问权限，如需要访问，请联系管理员");
            resp.setHeader("Content-Type", "application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(JsonMapper.obj2String(jsonData));
        } else {
            clientRedirect(noAuthUrl, resp);
            return ;
        }
    }

    private void clientRedirect(String url, HttpServletResponse resp) throws IOException {
        resp.setHeader("Content-Type", "text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//" +
                "EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta " +
                "http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" +
                "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window." +
                "location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }

    @Override
    public void destroy() {

    }
}
