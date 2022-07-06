package com.twosmall.constellation.controller;


import com.alibaba.fastjson.JSON;
import com.twosmall.constellation.entity.dao.OrderDao;
import com.twosmall.constellation.entity.param.PrintTableParam;
import com.twosmall.constellation.entity.result.RestResult;
import com.twosmall.constellation.service.GuGuBirdExtendsService;
import com.twosmall.constellation.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author enzo
 * @since 2022-07-05
 */
@Slf4j
@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private GuGuBirdExtendsService guGuBirdExtendsService;

    /**
     * 下单操作
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("addOrder")
    @CrossOrigin()
    public RestResult<String> addMenuItem(@RequestBody PrintTableParam param) {
        List<Map<String, String>> menus = param.getMenus();
        OrderDao orderDao = new OrderDao();
        orderDao.setOrderNumber(param.getOrderNumber());
        orderDao.setOrderDetails(JSON.toJSONString(param.getMenus()));
        orderDao.setTableId(param.getTableId());
        orderDao.setTotalPrice(new BigDecimal(param.getTotalPrice()));
        orderService.save(orderDao);
        List<Map<String, String>> printMenus = new ArrayList<>();
        for (Map<String, String> menu : menus) {
            String id = menu.get("id");
            String num = menu.get("num");
            String itemName = menu.get("itemName");
            String unitPrice = menu.get("unitPrice");
            Map<String, String> menuItem = new HashMap<>();
            menuItem.put(itemName, num);
            printMenus.add(menuItem);
        }
        param.setMenus(printMenus);
        guGuBirdExtendsService.printTable(param);
        return RestResult.wrapSuccessResponse();
    }

}

