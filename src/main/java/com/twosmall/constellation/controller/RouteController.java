package com.twosmall.constellation.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@CrossOrigin
public class RouteController {

    /**
     * 配置
     *
     * @return 页面
     */
    @RequestMapping("/tabbar")
    public String tabbar() {
        return "tabbar";
    }

    /**
     * 系统配置
     *
     * @return 页面
     */
    @RequestMapping("/system")
    public String system() {
        return "system";
    }

    /**
     * 新增菜单
     *
     * @return 页面
     */
    @RequestMapping("/addMenuPage")
    public String addMenuPage() {
        return "addMenu";
    }

    /**
     * 编辑菜单
     *
     * @return 页面
     */
    @RequestMapping("/editMenuPage")
    public String editMenuPage() {
        return "editMenu";
    }

    /**
     * 菜单列表
     *
     * @return 页面
     */
    @RequestMapping("/listMenu")
    public String listMenu() {
        return "listMenu";
    }

    /**
     * 餐桌列表
     *
     * @return 页面
     */
    @RequestMapping("/listTable")
    public String listTable() {
        return "listTable";
    }

    /**
     * 新增分类
     *
     * @return 页面
     */
    @RequestMapping("/addCategory")
    public String category() {
        return "addCategory";
    }

    /**
     * 分类列表
     *
     * @return 页面
     */
    @RequestMapping("/listCategory")
    public String listCategory() {
        return "listCategory";
    }

    /**
     * 新增座位
     *
     * @return 页面
     */
    @RequestMapping("/addTable")
    public String addTable() {
        return "addTable";
    }
}
