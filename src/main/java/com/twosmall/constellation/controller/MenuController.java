package com.twosmall.constellation.controller;


import com.twosmall.constellation.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 应用配置表 前端控制器
 * </p>
 *
 * @author enzo
 * @since 2021-10-27
 */
@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/testMenu")
    public String index1() {

        return "order/orderIndex";
    }

}

