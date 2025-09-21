package com.waiit.yun_picture_backed.model.dto.space;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceDeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * Space id
     */
    private Long spaceId;

    private static final long serialVersionUID = 1L;
}