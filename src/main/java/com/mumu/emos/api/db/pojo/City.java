package com.mumu.emos.api.db.pojo;

import lombok.Data;

/**
 * 疫情城市列表
 */
@Data
public class City {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 拼音简称
     */
    private String code;
}