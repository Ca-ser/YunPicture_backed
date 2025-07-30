package com.waiit.yun_picture_backed.model.dto.picture;

import lombok.Data;

import java.io.Serializable;

@Data
public class PictureUploadRequest implements Serializable {
    /**
     * 图片的id
     */
    private Long id;

    /**
     * 图片地址
     */
    private String fileUrl;
    

}
