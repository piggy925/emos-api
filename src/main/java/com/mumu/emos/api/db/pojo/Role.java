package com.mumu.emos.api.db.pojo;

import lombok.Data;

/**
 * 角色表
 */
@Data
public class Role {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限集合
     */
    private String permissions;

    /**
     * 描述
     */
    private String desc;

    /**
     * 系统角色内置权限
     */
    private String defaultPermissions;

    /**
     * 是否为系统内置角色
     */
    private Boolean systemic;
}