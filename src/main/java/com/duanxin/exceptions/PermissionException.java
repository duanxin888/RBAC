package com.duanxin.exceptions;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName PermissionException
 * @Description 自定义异常
 * @date 2019/7/17 8:04
 */
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }

    protected PermissionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
