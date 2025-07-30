package com.waiit.yun_picture_backed.manager.upload;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.exception.ThrowUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


/*
 * URL文件上传
 * */
@Service
public class UrlPictureUpload extends PictureUploadTempplete {
    @Override
    protected void processFile(Object inputSource, File file) throws IOException, Exception {
        String fileUrl = (String) inputSource;
        HttpUtil.downloadFile(fileUrl, file);


    }

    @Override
    protected String getOriginalFilename(Object inputSource) {
        String fileUrl = (String) inputSource;
        return FileUtil.mainName(fileUrl);
    }

    @Override
    protected void validPicture(Object inputSource) {
        String fileUrl = (String) inputSource;
        //1. 校验参数
        ThrowUtils.throwIf(StrUtil.isBlank(fileUrl), ErrorCode.PARAMS_ERROR, "文件地址为空");
        //2. 校验Url 格式
        try {
            new URL(fileUrl);
        } catch (MalformedURLException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件地址格式不正确");
        }
        //3. 发送HEAD 校验Url 是否可以访问
        ThrowUtils.throwIf(!(fileUrl.startsWith("http://") || fileUrl.startsWith("https://")),
                ErrorCode.PARAMS_ERROR, "仅支持 HTTP 或 HTTPS 协议的文件地址");
        HttpResponse httpHead = null;
        try {
            httpHead = HttpUtil.createRequest(Method.HEAD, fileUrl).execute();
            // 未能正常返回，无需执行其他判断
            if (httpHead.getStatus() != HttpStatus.HTTP_OK) {
                return;
            }
            //4. 文件存在, 校验文件类型
            String contentType = httpHead.header("Content-Type");
            // 不为空校验是否合法
            if (StrUtil.isNotBlank(contentType)) {
                // 被允许的类型
                final List<String> ALLOW_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png", "image/webp");
                ThrowUtils.throwIf(!ALLOW_CONTENT_TYPES.contains(contentType.toLowerCase()),
                        ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
            //5. 文件存在, 文件大小校验
            String contentLengthStr = httpHead.header("Content-Length");
            if (StrUtil.isNotBlank(contentLengthStr)) {
                try {
                    long ContentLength = Long.parseLong(contentLengthStr);
                    final long TWO_MB = 2 * 1024 * 1024L;
                    ThrowUtils.throwIf(ContentLength > TWO_MB, ErrorCode.PARAMS_ERROR, "文件超过2MB");

                } catch (NumberFormatException e) {
                    throw new BusinessException(ErrorCode.PARAMS_ERROR, "文件大小格式错误");
                }

            }


        } finally {
            // 资源释放，当文件不存在时
            if (httpHead != null) {
                httpHead.close();
            }

        }

    }

}

