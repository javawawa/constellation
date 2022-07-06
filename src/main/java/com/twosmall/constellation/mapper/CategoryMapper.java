package com.twosmall.constellation.mapper;

import com.twosmall.constellation.entity.dao.CategoryDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author enzo
 * @since 2022-06-08
 */
public interface CategoryMapper extends BaseMapper<CategoryDao> {

    @Update("update gerp_category set deleted = 1 where id = #{id}")
    int logicDeleteById(int id);

}
