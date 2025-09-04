package com.waiit.yun_picture_backed.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.waiit.yun_picture_backed.model.dto.picture.PictureQueryRequest;
import com.waiit.yun_picture_backed.model.entity.Picture;
import com.waiit.yun_picture_backed.model.vo.PictureVO;

public interface CacheService extends IService<Picture> {

    String getCache(String redisKey);
    Boolean putCache(String cacheValue,String redisKey);
}
