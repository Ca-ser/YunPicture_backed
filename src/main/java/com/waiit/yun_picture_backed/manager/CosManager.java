package com.waiit.yun_picture_backed.manager;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
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
     *
     * @param key 唯一标识
     * @return COSObject
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }

    /**
     * 上传图片，同时附带图片信息
     *
     * @param key  唯一键
     * @param file 文件
     */
    public PutObjectResult putPictureObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key, file);
        //对图片进行处理(获取基本信息，也被视为对图片的处理)
        PicOperations picOperations = new PicOperations();
        // 返回图片的基本信息
        picOperations.setIsPicInfo(1);
        // 构造处理参数怒
        putObjectRequest.setPicOperations(picOperations);

        return cosClient.putObject(putObjectRequest);
    }


}
