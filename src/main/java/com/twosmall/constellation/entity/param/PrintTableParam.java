package com.twosmall.constellation.entity.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author enzo
 * @date 2021/11/30 4:39 下午
 * @description 打印表格
 */
@Data
public class PrintTableParam implements Serializable {

    /**
     * 桌号
     */
    private String tableNumber;

    /**
     * id
     */
    private Integer tableId;

    /**
     * 订单号
     */
    private String orderNumber;

    private String totalPrice;

    /**
     * 菜单
     */
    private List<Map<String, String>> menus;

}
