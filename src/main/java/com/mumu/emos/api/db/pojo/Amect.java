package com.mumu.emos.api.db.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 罚金表
 *
 * @author mumu
 * @date 2022/1/1
 */
@Data
public class Amect {
    /**
     * 主键
     */
    private Integer id;

    /**
     * UUID
     */
    private String uuid;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 罚款金额
     */
    private BigDecimal amount;

    /**
     * 罚款类型
     */
    private Byte typeId;

    /**
     * 罚款原因
     */
    private String reason;

    /**
     * 微信支付单ID
     */
    private String prepayId;

    /**
     * 状态：1未缴纳，2已缴纳
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;
}