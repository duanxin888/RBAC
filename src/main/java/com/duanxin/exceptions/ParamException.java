package com.duanxin.exceptions;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName ParamException
 * @Description 自定义校验异常
 * @date 2019/7/17 11:56
 */
public class ParamException extends RuntimeException {
    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParamException(Throwable cause) {
        super(cause);
    }

    protected ParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
