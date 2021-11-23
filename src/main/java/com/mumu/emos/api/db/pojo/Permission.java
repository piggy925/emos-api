package com.mumu.emos.api.db.pojo;

import lombok.Data;

@Data
public class Permission {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 权限
     */
    private String permissionName;

    /**
     * 模块ID
     */
    private Integer moduleId;

    /**
     * 行为ID
     */
    private Integer actionId;
}