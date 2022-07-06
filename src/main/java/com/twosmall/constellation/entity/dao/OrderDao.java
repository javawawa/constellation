package com.twosmall.constellation.entity.dao;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author enzo
 * @since 2022-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gerp_order")
public class OrderDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    @TableField("order_number")
    private String orderNumber;

    /**
     * 桌号
     */
    @TableField("table_id")
    private Integer tableId;

    /**
     * 总价格
     */
    @TableField("total_price")
    private BigDecimal totalPrice;

    /**
     * 订单详情
     */
    @TableField("order_details")
    private String orderDetails;

    /**
     * 创建人
     */
    @TableField("create_people")
    private String createPeople;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("modify_people")
    private String modifyPeople;

    /**
     * 更新时间
     */
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    /**
     * 逻辑删除;1/已删除 0/正常
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
