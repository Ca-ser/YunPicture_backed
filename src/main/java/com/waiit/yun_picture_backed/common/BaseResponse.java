package com.waiit.yun_picture_backed.common;

import com.waiit.yun_picture_backed.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;
@Data
public class BaseResponse<T> implements Serializable {
    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());

    }
}
