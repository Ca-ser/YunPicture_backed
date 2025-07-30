package com.waiit.yun_picture_backed.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.waiit.yun_picture_backed.model.dto.picture.PictureQueryRequest;
import com.waiit.yun_picture_backed.model.dto.picture.PictureReviewRequest;
import com.waiit.yun_picture_backed.model.dto.picture.PictureUploadRequest;
import com.waiit.yun_picture_backed.model.entity.Picture;
import com.waiit.yun_picture_backed.model.entity.User;
import com.waiit.yun_picture_backed.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Restart
 * @description 针对表【picture(图片)】的数据库操作Service
 * @createDate 2025-07-06 18:12:28
 */
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片  可重复上传
     *
     * @param inputSource       图片文件
     * @param pictureUploadRequest 图片上传请求
     * @param loginUser            登录用户
     * @return 图片信息
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

    /**
     * 获取查询对象
     *
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片包装类
     *
     * @param picture 图片
     * @param request HTTP请求
     * @return 图片包装类
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 获取图片包装类（分页）
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片
     *
     * @param picture
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     * */
    void  doPictureReview(PictureReviewRequest pictureReviewRequest,User loginUser);
    
    /**
     * 修改状态
     * */
    void fillReviewParams(Picture picture, User loginUser);
}   
