package com.duanxin.beans;

import lombok.Getter;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName CacheKeyConstants
 * @Description
 * @date 2019/7/24 9:00
 */
@Getter
public enum  CacheKeyConstants {

    /**
     * 系统权限前缀
     * */
    SYSTEM_ACLS,

    /**
     * 用户权限前缀
     * */
    USER_ACLS;
}
