package com.waiit.yun_picture_backed.manager.upload;

import cn.hutool.core.io.FileUtil;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.exception.ThrowUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/*
* 文件上传
* */
@Service
public class FilePictureUpload extends PictureUploadTempplete {
    @Override
    protected void processFile(Object inputSource, File file) throws Exception {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        multipartFile.transferTo(file);
        
    }

    @Override
    protected String getOriginalFilename(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        return multipartFile.getOriginalFilename();
    }

    @Override
    protected void validPicture(Object inputSource) {
        MultipartFile multipartFile = (MultipartFile) inputSource;
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.NOT_FOUND_ERROR, "文件为空");
        // 校验文件大小
        final long MAX_SIZE = 1024 * 1024;
        long fileSize = multipartFile.getSize();
        ThrowUtils.throwIf(fileSize > 5 * MAX_SIZE, ErrorCode.PARAMS_ERROR, "文件过大");
        // 校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        System.out.println(fileSuffix);
        // 允许上传的文件后缀列表
        final List<String> ALLOWED_SUFFIXES = Arrays.asList("jpg", "jpeg", "png", "webp");
        ThrowUtils.throwIf(!ALLOWED_SUFFIXES.contains(fileSuffix), ErrorCode.PARAMS_ERROR, "文件类型错误");
    }
}
