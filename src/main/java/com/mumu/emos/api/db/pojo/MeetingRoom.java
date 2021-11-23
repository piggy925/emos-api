package com.mumu.emos.api.db.pojo;

import lombok.Data;

/**
 * 会议室表
 */
@Data
public class MeetingRoom {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 会议室名称
     */
    private String name;

    /**
     * 最大人数
     */
    private Short max;

    /**
     * 备注
     */
    private String desc;

    /**
     * 状态，0不可用，1可用
     */
    private Byte status;
}