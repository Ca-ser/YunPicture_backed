package com.waiit.yun_picture_backed.common;

import com.waiit.yun_picture_backed.exception.ErrorCode;

public class ResultUtils {
    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 响应
     */
    public static <T> BasieResponse<T> success(T data) {
        return new BasieResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static BasieResponse<?> error(ErrorCode errorCode) {
        return new BasieResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 响应
     */
    public static BasieResponse<?> error(int code, String message) {
        return new BasieResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码
     * @return 响应
     */
    public static BasieResponse<?> error(ErrorCode errorCode, String message) {
        return new BasieResponse<>(errorCode.getCode(), null, message);
    }
    
}
