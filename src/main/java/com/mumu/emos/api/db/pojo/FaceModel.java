package com.mumu.emos.api.db.pojo;

import lombok.Data;

@Data
public class FaceModel {
    /**
     * 主键值
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户人脸模型
     */
    private String faceModel;
}