package com.twosmall.constellation.service;

import com.twosmall.constellation.entity.dao.CategoryDao;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
public interface ICategoryService extends IService<CategoryDao> {

     int logicDeleteById(Integer id);

}
