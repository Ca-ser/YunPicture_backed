package com.waiit.yun_picture_backed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.waiit.yun_picture_backed.model.dto.user.UserQueryRequest;
import com.waiit.yun_picture_backed.model.dto.user.UserRegisterRequest;
import com.waiit.yun_picture_backed.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.waiit.yun_picture_backed.model.vo.LoginUserVO;
import com.waiit.yun_picture_backed.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Restart
 * &#064;description  针对表【user(用户)】的数据库操作Service
 * &#064;createDate  2025-06-29 10:44:41
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册请求
     * @return 用户id
     */
    Long userRegister(UserRegisterRequest userRegisterRequest);

    /**
     * 用户登录
     *
     * @param userAccount  用户账号
     * @param userPassword 用户密码
     * @param request      请求
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取加密后的密码
     *
     * @param userPassword 用户密码
     * @return 加密后的密码
     */
    String getEncryptPassword(String userPassword);

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取脱敏后的 用户登录信息
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */

    LoginUserVO getloginUserVO(User user);
    
    
    /**
     * 获取脱敏后的用户信息
     *
     * @param user 用户信息
     * @return 脱敏后的用户信息
     */

    UserVO getUserVO(User user);
    /**
     * 获取脱敏后的用户信息列表
     *
     * @param userList 用户信息列表
     * @return 脱敏后的用户信息
     */

    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 用户注销
     * @param request
     * @return
     * */
     Boolean userLogout(HttpServletRequest request);

    /**
     * 分页查询用户信息
     *
     * @param userQueryRequest 用户查询请求
     * @return 用户信息列表
     */
     QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

}
