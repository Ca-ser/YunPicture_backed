package com.waiit.yun_picture_backed.model.vo;

import lombok.Data;

import java.util.List;


/**
 * 标签分类 列表视图
 */
@Data
public class PictureTagCategory {

    /**
     * 标签列表
     */
    private List<String> tagList;
    /**
     * 分类列表
     */
    private List<String> categoryList;
}
