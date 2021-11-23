package com.mumu.emos.api.db.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 罚金类型表
 */
@Data
public class AmectType {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 类别
     */
    private String type;

    /**
     * 罚金
     */
    private BigDecimal money;

    /**
     * 是否为系统内置
     */
    private Boolean systemic;
}