package com.duanxin.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName ApplicationContextHelper
 * @Description 获取springApplicationContext上下文类
 * @date 2019/7/17 15:24
 */
@Component("applicationContextHelper")
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext context ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * @description 通过参数类型获取bean对象
     * @param [cls] 参数类型
     * @date 2019/7/17 15:34
     * @return T
     **/
    public static <T> T popBean(Class<T> cls) {
        if (context == null) {
            return null;
        }
        return context.getBean(cls);
    }

    /**
     * @description 通过参数名和参数类型获取bean对象
     * @param [name, cls] 【参数名，参数类型】
     * @date 2019/7/17 15:34
     * @return T
     **/
    public static <T> T popBean(String name, Class<T> cls) {
        if (context == null) {
            return null;
        }

        return context.getBean(name, cls);
    }
}
