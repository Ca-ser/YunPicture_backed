package com.waiit.yun_picture_backed.model.dto.user;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求
 */
@Data
public class UserLoginRequest implements Serializable {
    
    private static final long serialVersionUID = -92936651537005971L;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;

    
}
