package com.duanxin.commons;

import com.duanxin.model.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName RequestHolder
 * @Description 请求高并发
 * @date 2019/7/20 10:06
 */
public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requsetHolder = new ThreadLocal<>();

    /**
     * @description 将SysUser放入线程池中
     * @param [sysUser]
     * @date 2019/7/20 10:10
     **/
    public static void set(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    /**
     * @description 将request放入线程池中
     * @param [request]
     * @date 2019/7/20 10:11
     **/
    public static void set(HttpServletRequest request) {
        requsetHolder.set(request);
    }

    /**
     * @description 从线程池中获取SysUser对象
     * @param []
     * @date 2019/7/20 10:12
     * @return com.duanxin.model.SysUser
     **/
    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    /**
     * @description 从线程池中获取request对象
     * @param []
     * @date 2019/7/20 10:13
     * @return javax.servlet.http.HttpServletRequest
     **/
    public static HttpServletRequest getCurrentRequest() {
        return requsetHolder.get();
    }

    /**
     * @description 从线程池中移除对象
     * @param []
     * @date 2019/7/20 10:14
     **/
    public static void remove() {
        userHolder.remove();
        requsetHolder.remove();
    }
}
