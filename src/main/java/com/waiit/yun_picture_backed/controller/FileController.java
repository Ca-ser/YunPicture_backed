package com.waiit.yun_picture_backed.controller;


import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.waiit.yun_picture_backed.annotation.AuthCHeck;
import com.waiit.yun_picture_backed.common.BasieResponse;
import com.waiit.yun_picture_backed.common.ResultUtils;
import com.waiit.yun_picture_backed.constant.UserConstant;
import com.waiit.yun_picture_backed.exception.BusinessException;
import com.waiit.yun_picture_backed.exception.ErrorCode;
import com.waiit.yun_picture_backed.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private CosManager cosManager;


    /**
     * 测试上传文件
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("test/upload")
    public BasieResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {

        // 文件目录
        String filename = multipartFile.getOriginalFilename();
        String filepath = String.format("/test/%s", filename);
        File file = null;

        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            cosManager.putObject(filepath, file);
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "文件上传失败");

        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = " + filepath);
                }
            }
        }

    }
    /**
     * 测试下载文件
     */
    @AuthCHeck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("test/download")
    public void  testDownLoadFile(String filePath , HttpServletResponse response){
        COSObject cosObject = cosManager.getObject(filePath);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        try {
            byte[] byteArray = IOUtils.toByteArray(cosObjectInput);
            // 设置相应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=" + filePath);
            // 写入相应
            response.getOutputStream().write(byteArray);
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
