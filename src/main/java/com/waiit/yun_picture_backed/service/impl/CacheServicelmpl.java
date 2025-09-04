package com.waiit.yun_picture_backed.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waiit.yun_picture_backed.common.ResultUtils;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.mapper.PictureMapper;
import com.waiit.yun_picture_backed.model.dto.picture.PictureQueryRequest;
import com.waiit.yun_picture_backed.model.entity.Picture;
import com.waiit.yun_picture_backed.model.vo.PictureVO;
import com.waiit.yun_picture_backed.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServicelmpl extends ServiceImpl<PictureMapper, Picture> implements CacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getCache(String redisKey) {


        //从redis中查询
        ValueOperations<String,String> valueOps = stringRedisTemplate.opsForValue();
        String cachedValue = valueOps.get(redisKey);
        //String cacheValue = LOCAL_CACHE.getIfPresent(redisKey);
        return cachedValue;
    }

    @Override
    public Boolean putCache(String cacheValue , String redisKey) {

        int cacheExpireTime = 300 + RandomUtil.randomInt(0,300);
        ValueOperations<String,String> valueOps = stringRedisTemplate.opsForValue();
        try {
            valueOps.set(redisKey,cacheValue,cacheExpireTime, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

    }
}
