package com.agsword.asbi.model.dto.order;

import com.agsword.asbi.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 次数订单表
 * @author as
 * @TableName ai_frequency_order
 */

@Data
public class AiFrequencyOrderQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 购买数量
     */
    private Long purchaseQuantity;

    /**
     * 单价
     */
    private Double price;

    /**
     * 交易金额
     */
    private Double totalAmount;

    /**
     * 交易状态【0->待付款；1->已完成；2->无效订单】
     */
    private Integer orderStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}