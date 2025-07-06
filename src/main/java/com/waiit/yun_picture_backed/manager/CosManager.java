package com.waiit.yun_picture_backed.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.waiit.yun_picture_backed.config.CosClientConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;

@Component
public class CosManager {
    @Resource
    private CosClientConfig cosClientConfig;
    @Resource
    private COSClient cosClient;

    /**
     * 上传文件到腾讯云COS
     *
     * @param key       文件名
     * @param localFile 本地文件
     * @return PutObjectResult
     */
    public PutObjectResult putObject(String key, File localFile) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, localFile);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 从腾讯上下载文件
     * @param key 唯一标识
     * @return 
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }


}
