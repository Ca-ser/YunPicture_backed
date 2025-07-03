package com.waiit.yun_picture_backed.controller;

import com.waiit.yun_picture_backed.common.BasieResponse;
import com.waiit.yun_picture_backed.common.ResultUtils;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.exception.ThrowUtils;
import com.waiit.yun_picture_backed.model.dto.UserLoginRequest;
import com.waiit.yun_picture_backed.model.dto.UserRegisterRequest;
import com.waiit.yun_picture_backed.model.entity.User;
import com.waiit.yun_picture_backed.model.vo.LoginUserVO;
import com.waiit.yun_picture_backed.service.UserService;
import org.apache.ibatis.annotations.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/regisier")
    @ResponseBody
    public BasieResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        Long registerResult = userService.userRegister(userRegisterRequest);
        return ResultUtils.success(registerResult);
    }


    @PostMapping("/login")
    @ResponseBody
    public BasieResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }


    @GetMapping("/get/lgoin")
    public BasieResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getloginUserVO(loginUser));
    }

    @PostMapping("/logout")
    @ResponseBody
    public BasieResponse<Boolean> userLogin(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求为空");
        }
        return ResultUtils.success(userService.userLogout(request));
    }
}

