package com.mumu.emos.api.db.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Workday {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 日期
     */
    private Date date;
}