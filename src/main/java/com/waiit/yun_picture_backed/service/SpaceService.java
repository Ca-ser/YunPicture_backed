package com.waiit.yun_picture_backed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.waiit.yun_picture_backed.model.dto.space.SpaceAddRequest;
import com.waiit.yun_picture_backed.model.dto.space.SpaceQueryRequest;
import com.waiit.yun_picture_backed.model.entity.Space;
import com.waiit.yun_picture_backed.model.entity.User;
import com.waiit.yun_picture_backed.model.vo.SpaceVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Kane_
* @description 针对表【space(空间)】的数据库操作Service
* @createDate 2025-09-06 22:16:50
*/
public interface SpaceService extends IService<Space> {


    /**
     * 获取查询对象
     *
     * @param spaceQueryRequest
     * @return
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取图片包装类
     *
     * @param space   图片
     * @param request HTTP请求
     * @return 图片包装类
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param spacePage
     * @param request
     * @return
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    /**
     * 校验图片
     *
     * @param space
     * @param add 是否为创建时校验
     */
    void validSpace(Space space,boolean add);


    /**
     * 自动填充限额数据
     * @param space
     */
    void fillSpaceBySpaceLevel(Space space);

    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);
}
