package com.duanxin.commons;

import com.duanxin.exceptions.ParamException;
import com.duanxin.exceptions.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SpringExceptionResolver
 * @Description 全局异常处理类
 * @date 2019/7/17 7:59
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 获取url
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System Error";

        // 对请求进行判断.json 或者 .page
        // 这里要求项目中所有请求json数据，都使用.json结尾
        if (url.endsWith(".json")) {
            // 异常是自定义异常
            if (ex instanceof PermissionException || ex instanceof ParamException) {
                JsonData result = JsonData.fail(ex.getMessage());
                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }

            log.error("unknown json exception url:" + url, ex);
            // 这里要求项目中所有请求page页面，都使用.page结尾
        } else if (url.endsWith(".page")) {

            log.error("unknown page exception url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("exception", result.toMap());
        } else {

            log.error("unknown other exception url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }
        return mv;
    }
}
