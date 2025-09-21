package com.waiit.yun_picture_backed.controller;

import com.waiit.yun_picture_backed.annotation.AuthCheck;
import com.waiit.yun_picture_backed.common.BaseResponse;
import com.waiit.yun_picture_backed.common.DeleteRequest;
import com.waiit.yun_picture_backed.common.ResultUtils;
import com.waiit.yun_picture_backed.constant.UserConstant;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.exception.ThrowUtils;
import com.waiit.yun_picture_backed.model.dto.space.SpaceAddRequest;
import com.waiit.yun_picture_backed.model.dto.space.SpaceDeleteRequest;
import com.waiit.yun_picture_backed.model.dto.space.SpaceUpdateRequest;
import com.waiit.yun_picture_backed.model.entity.Space;
import com.waiit.yun_picture_backed.model.entity.SpaceLevel;
import com.waiit.yun_picture_backed.model.entity.User;
import com.waiit.yun_picture_backed.model.enums.SpaceLevelEnum;
import com.waiit.yun_picture_backed.service.SpaceService;
import com.waiit.yun_picture_backed.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/space")
public class SpaceController {
    @Resource
    private SpaceService spaceService;
    @Resource
    private UserService userService;

    /**
     * 查询空间级别
     *
     * @return
     */
    @GetMapping("/list/level")
    public BaseResponse<List<SpaceLevel>> listSpaceLevel() {
        List<SpaceLevel> spaceLevelList = Arrays.stream(SpaceLevelEnum.values()) // 获取所有枚举
                .map(spaceLevelEnum -> new SpaceLevel(
                        spaceLevelEnum.getValue(),
                        spaceLevelEnum.getText(),
                        spaceLevelEnum.getMaxCount(),
                        spaceLevelEnum.getMaxSize()))
                .collect(Collectors.toList());
        return ResultUtils.success(spaceLevelList);
    }


    /**
     * 创建私有空间
     *
     * @param spaceAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addSpace(@RequestBody SpaceAddRequest spaceAddRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(spaceAddRequest == null, ErrorCode.PARAMS_ERROR);
        User loginUser = userService.getLoginUser(request);
        long newId = spaceService.addSpace(spaceAddRequest, loginUser);
        return ResultUtils.success(newId);
    }


    /**
     * 空间更新接口
     *
     * @param spaceUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateSpace(@RequestBody SpaceUpdateRequest spaceUpdateRequest) {
        if (spaceUpdateRequest == null || spaceUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 转换数据 将实体类 与 DTO 类进行转换
        Space space = new Space();
        BeanUtils.copyProperties(spaceUpdateRequest, space);
        // 自动填充数据
        spaceService.fillSpaceBySpaceLevel(space);
        // 数据校验
        spaceService.validSpace(space, false);
        // 判断健全
        Long id = spaceUpdateRequest.getId();
        Space oldSpace = spaceService.getById(id);
        ThrowUtils.throwIf(oldSpace == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = spaceService.updateById(space);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSpace(@RequestBody SpaceDeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User loginUser = userService.getLoginUser(request);
        spaceService.deleteSpace(deleteRequest, loginUser);
        return ResultUtils.success(true);


    }
}
