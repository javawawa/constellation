package com.twosmall.constellation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.twosmall.constellation.entity.dao.CategoryDao;
import com.twosmall.constellation.entity.dao.MenuDao;
import com.twosmall.constellation.entity.param.AddMenuParam;
import com.twosmall.constellation.entity.param.IdParam;
import com.twosmall.constellation.entity.result.CategoryAndMenuResult;
import com.twosmall.constellation.entity.result.RestResult;
import com.twosmall.constellation.service.ICategoryService;
import com.twosmall.constellation.service.IMenuService;
import com.twosmall.constellation.utils.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用配置表 前端控制器
 * </p>
 *
 * @author enzo
 * @since 2021-10-27
 */
@Slf4j
@CrossOrigin
@RestController
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private OssUtil ossUtil;


    /**
     * 新增菜单
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("addMenuItem")
    @CrossOrigin()
    public RestResult<String> addMenuItem(@RequestBody AddMenuParam param) {
        MenuDao menuDao = new MenuDao();
        menuDao.setMenuName(param.getMenuName());
        menuDao.setClassifyId(param.getCategory());
        menuDao.setPhoto(param.getUrl());
        menuDao.setPrice(Double.parseDouble(param.getPrice().toString()));
        menuDao.setOrderNum(param.getOrder());
        menuDao.setClassifyName(param.getCategoryName());
        menuService.save(menuDao);
        return RestResult.wrapSuccessResponse();
    }

    /**
     * 菜单详情
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("getMenuItem")
    @CrossOrigin()
    public RestResult<MenuDao> getMenuItem(@RequestBody IdParam param) {
        MenuDao menuDao = menuService.getById(param.getId());
        String urlByExpireUrlOrKey = ossUtil.getUrlByExpireUrlOrKey(menuDao.getPhoto(), null);
        menuDao.setPhoto(urlByExpireUrlOrKey);
        return RestResult.wrapSuccessResponse(menuDao);
    }

    /**
     * 新增菜单
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("editMenuItem")
    @CrossOrigin()
    public RestResult<Boolean> editMenuItem(@RequestBody AddMenuParam param) {
        MenuDao menuDao = menuService.getById(param.getId());
        menuDao.setMenuName(param.getMenuName());
        menuDao.setClassifyId(param.getCategory());
        menuDao.setPhoto(param.getUrl());
        menuDao.setPrice(Double.parseDouble(param.getPrice().toString()));
        menuDao.setOrderNum(param.getOrder());
        menuDao.setClassifyName(param.getCategoryName());
        return RestResult.wrapSuccessResponse(menuService.updateById(menuDao));
    }

    /**
     * 删除菜单
     *
     * @param param 参数
     * @return 结果
     */
    @PostMapping("deleteMenuItem")
    @CrossOrigin()
    public RestResult<Boolean> deleteMenuItem(@RequestBody IdParam param) {
        return RestResult.wrapSuccessResponse(menuService.removeById(param.getId()));
    }

    /**
     * 获取菜单和分类
     *
     * @return 结果
     */
    @RequestMapping("getCategoryAndMenu")
    @ResponseBody
    public List<CategoryAndMenuResult> getCategoryAndMenu() {
        List<CategoryAndMenuResult> resultList = new ArrayList<>();
        QueryWrapper<CategoryDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<CategoryDao> list = iCategoryService.list(queryWrapper);
        for (CategoryDao categoryDao : list) {
            CategoryAndMenuResult categoryAndMenuResult = new CategoryAndMenuResult();
            categoryAndMenuResult.setCategoryName(categoryDao.getCategoryName());
            categoryAndMenuResult.setOrder(categoryDao.getOrderNum());
            categoryAndMenuResult.setId(categoryDao.getId());
            //查询菜单
            QueryWrapper<MenuDao> menuDaoQueryWrapper = new QueryWrapper<>();
            menuDaoQueryWrapper.orderByAsc("order_num");
            menuDaoQueryWrapper.eq("classify_id", categoryDao.getId());
            List<MenuDao> menuDaoList = menuService.list(menuDaoQueryWrapper);
            List<CategoryAndMenuResult.FoodListDTO> foodList = new ArrayList<>();
            for (MenuDao menuDao : menuDaoList) {
                CategoryAndMenuResult.FoodListDTO foodListDTO = new CategoryAndMenuResult.FoodListDTO();
                foodListDTO.setMenuName(menuDao.getMenuName());
                foodListDTO.setId(menuDao.getId());
                foodListDTO.setClassifyId(menuDao.getClassifyId());
                String urlByExpireUrlOrKey = ossUtil.getUrlByExpireUrlOrKey(menuDao.getPhoto(), null);
                foodListDTO.setPhoto(urlByExpireUrlOrKey);
                foodListDTO.setPrice(menuDao.getPrice().toString());
                foodListDTO.setOrderNum(menuDao.getOrderNum());
                foodList.add(foodListDTO);
            }
            categoryAndMenuResult.setFoodList(foodList);
            resultList.add(categoryAndMenuResult);
        }
        return resultList;
    }

    /**
     * 上传图片
     *
     * @param request http
     * @return url
     */
    @CrossOrigin()
    @RequestMapping("/uploadImage")
    public RestResult<?> uploadImage(MultipartHttpServletRequest request) {

        Map<String, MultipartFile> fileMap = request.getFileMap();
        if (CollectionUtils.isEmpty(fileMap)) {
            return RestResult.wrapErrorResponse("上传文件为空");
        }

        //开始处理图片组
        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
            MultipartFile multipartFile = entry.getValue();
            if (!multipartFile.isEmpty()) {
                byte[] bytes;
                try {
                    bytes = multipartFile.getBytes();
                    InputStream inputStream = new ByteArrayInputStream(bytes);
                    String originalFilename = ossUtil.getExtensionName(multipartFile.getOriginalFilename());
                    String url = ossUtil.uploadToOss(inputStream, originalFilename, null);
                    if (url == null) {
                        return RestResult.wrapErrorResponse("上传文件失败");
                    }
                    return RestResult.wrapSuccessResponse(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return RestResult.wrapErrorResponse("上传文件失败，处理失败！");
    }

}

