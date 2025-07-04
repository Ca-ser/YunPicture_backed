package com.waiit.yun_picture_backed.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waiit.yun_picture_backed.annotation.AuthCHeck;
import com.waiit.yun_picture_backed.common.BasieResponse;
import com.waiit.yun_picture_backed.common.DeleteRequest;
import com.waiit.yun_picture_backed.common.ResultUtils;
import com.waiit.yun_picture_backed.constant.UserConstant;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.exception.ThrowUtils;
import com.waiit.yun_picture_backed.model.dto.user.*;
import com.waiit.yun_picture_backed.model.entity.User;
import com.waiit.yun_picture_backed.model.vo.LoginUserVO;
import com.waiit.yun_picture_backed.model.vo.UserVO;
import com.waiit.yun_picture_backed.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest 用户注册请求
     * @return 用户id
     */
    @PostMapping("/regisier")
    @ResponseBody
    public BasieResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        Long registerResult = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(registerResult);
    }

    /**
     * 用户登录
     * @param userLoginRequest  用户登录请求
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    @PostMapping("/login")
    @ResponseBody
    public BasieResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     *  获取当前登录用户
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    @GetMapping("/get/lgoin")
    public BasieResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getloginUserVO(loginUser));
    }

    /**
     * 用户注销
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    @PostMapping("/logout")
    @ResponseBody
    public BasieResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求为空");
        }
        return ResultUtils.success(userService.userLogout(request));
    }

    /**
     * 添加用户
     * @param userAddRequest 用户添加请求
     * @return  脱敏后的用户信息
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/adduser")
    @ResponseBody
    public BasieResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求为空");
        }
        User user = new User();
        BeanUtil.copyProperties(userAddRequest, user);
        final String DEFAULT_PASSWORD = "thisdefaultpassword";
        user.setUserPassword(userService.getEncryptPassword(DEFAULT_PASSWORD));
        boolean save = userService.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加用户失败");
        } else {
            return ResultUtils.success(user.getId());
        }

    }

    /**
     * 管理员查询用户信息
     * @param id 用户id
     * @return 用户信息
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/get")
    public BasieResponse<User> getUserById(@RequestParam Long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 普通用户查询用户信息
     * @param id 查询的用户id
     * @return 脱敏后的用户信息
     */
    @GetMapping("/get/vo")
    public BasieResponse<UserVO> getUserVOById(@RequestParam Long id) {
        BasieResponse<User> userById = getUserById(id);
        User user = userById.getData();
        return ResultUtils.success(userService.getUserVO(user));
        
    }

    /**
     * 删除用户
     * @param deleteRequest 删除用户请求
     * @return 返回结果
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/delete")
    public BasieResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null && deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 更新用户信息
     * @param userUpdateRequest 更新用户信息请求
     * @return 返回结果
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/update")
    public BasieResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        if (userUpdateRequest == null && userUpdateRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        boolean b = userService.updateById(user);
        ThrowUtils.throwIf(!b,ErrorCode.OPERATION_ERROR,"更新用户失败");
        return ResultUtils.success(b);

    }

    /**
     * 分页查询用户信息
     * @param userQueryRequest
     * @return 返回用户列表
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/list/page/vo")
    public BasieResponse<Page<UserVO>> listUserVoByPage(@RequestBody UserQueryRequest userQueryRequest){
        ThrowUtils.throwIf(userQueryRequest == null,ErrorCode.PARAMS_ERROR);
        long current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize), userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return ResultUtils.success(userVOPage);
    }
}

