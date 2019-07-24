package com.duanxin.commons;

import com.duanxin.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName HttpInterceptor
 * @Description 自定义http拦截器
 * @date 2019/7/17 17:04
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        Map params = request.getParameterMap();
        long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        log.info("request start. url:{}, params:{}", url, JsonMapper.obj2String(params));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*String url = request.getRequestURL().toString();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request finished. url:{}, params:{}", url, end - start);*/

        removeThreadLocalInfo();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url = request.getRequestURL().toString();
        long start = (Long) request.getAttribute(START_TIME);
        long end = System.currentTimeMillis();
        log.info("request completed. url:{}, params:{}", url, end - start);
        removeThreadLocalInfo();
    }

    /**
     * @description 将请求对象从线程池中移除
     * @param []
     * @date 2019/7/20 10:19
     **/
    private void removeThreadLocalInfo() {
        RequestHolder.remove();
    }
}
