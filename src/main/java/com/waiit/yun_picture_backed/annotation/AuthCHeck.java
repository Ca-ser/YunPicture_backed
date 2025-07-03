package com.waiit.yun_picture_backed.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCHeck {


    /**
     * 必须具有某个角色
     * 
     * @return
     */
    String mustRole() default "";
    
    
}
