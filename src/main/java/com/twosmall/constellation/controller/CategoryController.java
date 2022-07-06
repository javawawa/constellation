package com.twosmall.constellation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.twosmall.constellation.entity.dao.CategoryDao;
import com.twosmall.constellation.entity.param.AddCategoryParam;
import com.twosmall.constellation.entity.param.EmptyParam;
import com.twosmall.constellation.entity.param.IdParam;
import com.twosmall.constellation.entity.result.CategoryAndMenuResult;
import com.twosmall.constellation.entity.result.CategoryDownResult;
import com.twosmall.constellation.entity.result.RestResult;
import com.twosmall.constellation.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;



    @PostMapping("addCategoryItem")
    @CrossOrigin()
    public RestResult<String> addCategoryItem(@RequestBody AddCategoryParam param) {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.setCategoryName(param.getCategoryName());
        categoryDao.setOrderNum(param.getOrder());
        iCategoryService.save(categoryDao);
        return RestResult.wrapSuccessResponse();
    }


    @PostMapping("getCategoryList")
    @CrossOrigin()
    public RestResult<List<CategoryDao>> getCategoryList(EmptyParam param) {
        QueryWrapper<CategoryDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<CategoryDao> list = iCategoryService.list(queryWrapper);
        return RestResult.wrapSuccessResponse(list);
    }

    @PostMapping("deleteCategoryItem")
    @CrossOrigin()
    public RestResult<String> deleteCategoryItem(@RequestBody IdParam param) {
        iCategoryService.removeById(param.getId());
        return RestResult.wrapSuccessResponse();
    }

    @PostMapping("getCategoryListDown")
    @CrossOrigin()
    public RestResult<List<CategoryDownResult>> getCategoryListDown(EmptyParam param) {
        QueryWrapper<CategoryDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_num");
        List<CategoryDao> list = iCategoryService.list(queryWrapper);
        List<CategoryDownResult> resultList = new ArrayList<>();
        for (CategoryDao categoryDao : list) {
            CategoryDownResult result = new CategoryDownResult();
            result.setTitle(categoryDao.getCategoryName());
            result.setValue(categoryDao.getId());
            resultList.add(result);
        }
        return RestResult.wrapSuccessResponse(resultList);
    }

}
