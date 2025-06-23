package com.waiit.yun_picture_backed.exception;

public class ThrowUtils {
    public static void throwIf(boolean condtion, RuntimeException runtimeException){
        if(condtion){
            throw runtimeException;
        }
    }
    public static void throwIf(boolean condtion, ErrorCode errorCode){
        throwIf(condtion, new BusinessException(errorCode));
    }
    public static void throwIf(boolean condtion, ErrorCode errorCode, String message){
        throwIf(condtion, new BusinessException(errorCode, message));
    }

}
