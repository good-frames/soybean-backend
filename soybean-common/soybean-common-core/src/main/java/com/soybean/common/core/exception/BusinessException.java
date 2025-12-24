package com.soybean.common.core.exception;

import com.soybean.common.core.constant.ResultCode;

public class BusinessException extends RuntimeException {
    private final int code;

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.PARAM_ERROR;
    }
}
