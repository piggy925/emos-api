package com.mumu.emos.api.db.pojo;

import lombok.Data;

/**
 * 模块资源表
 */
@Data
public class Module {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 模块编号
     */
    private String moduleCode;

    /**
     * 模块名称
     */
    private String moduleName;
}