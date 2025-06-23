package com.waiit.yun_picture_backed.common;

import com.waiit.yun_picture_backed.exception.ErrorCode;
import lombok.Data;

import java.io.Serializable;
@Data
public class BasieResponse<T> implements Serializable {
    private int code;

    private T data;

    private String message;

    public BasieResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BasieResponse(int code, T data) {
        this(code, data, "");
    }

    public BasieResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());

    }
}
