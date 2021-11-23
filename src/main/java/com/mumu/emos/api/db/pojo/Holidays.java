package com.mumu.emos.api.db.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 节假日表
 */
@Data
public class Holidays {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 日期
     */
    private Date date;
}