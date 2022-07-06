package com.twosmall.constellation.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.twosmall.constellation.entity.dao.TableDao;
import com.twosmall.constellation.entity.param.AddCategoryParam;
import com.twosmall.constellation.entity.result.RestResult;
import com.twosmall.constellation.entity.result.TableListResult;
import com.twosmall.constellation.service.ITableService;
import com.twosmall.constellation.utils.ImageUtils;
import com.twosmall.constellation.utils.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
@Slf4j
@RestController
@CrossOrigin
public class TableController {

    @Autowired
    private ITableService iTableService;

    @Value("${order.url}")
    private String orderUrl;

    @Autowired
    private OssUtil ossUtil;

    @PostMapping("addTable")
    @CrossOrigin()
    public RestResult<String> addTable(@RequestBody AddCategoryParam param) {
        TableDao tableDao = new TableDao();
        tableDao.setTableName(param.getCategoryName());
        iTableService.save(tableDao);
        Integer id = tableDao.getId();
        orderUrl = orderUrl + "?id=" + id;
        //生成二维码
        String tableImageUrl = ImageUtils.createTableImage(orderUrl, param.getCategoryName());
        tableDao.setTableCode(tableImageUrl);
        iTableService.updateById(tableDao);
        return RestResult.wrapSuccessResponse();
    }


    @PostMapping("getTableList")
    @CrossOrigin()
    public RestResult<List<TableListResult>> getTableList() {
        List<TableListResult> resultList = new ArrayList<>();
        QueryWrapper<TableDao> queryWrapper = new QueryWrapper<>();
        List<TableDao> list = iTableService.list(queryWrapper);
        for (TableDao tableDao : list) {
            TableListResult tableListResult = new TableListResult();
            tableListResult.setId(tableDao.getId());
            String codeUrl = ossUtil.getUrlByExpireUrlOrKey(tableDao.getTableCode(), null);
            tableListResult.setTableCode(codeUrl);
            tableListResult.setTableName(tableDao.getTableName());
            resultList.add(tableListResult);
        }
        return RestResult.wrapSuccessResponse(resultList);
    }
}

