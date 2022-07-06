package com.twosmall.constellation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twosmall.constellation.entity.dao.CategoryDao;
import com.twosmall.constellation.mapper.CategoryMapper;
import com.twosmall.constellation.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryDao> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int logicDeleteById( Integer id) {
        return categoryMapper.logicDeleteById(id);
    }
}
