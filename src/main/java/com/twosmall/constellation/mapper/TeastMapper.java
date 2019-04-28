package com.twosmall.constellation.mapper;

import com.twosmall.constellation.entity.dao.TeastDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gep
 * @since 2019-04-26
 */
public interface TeastMapper extends BaseMapper<TeastDao> {

    @Select("select * from gerp_teast where status = 0 order by rand() limit 1")
    TeastDao queryByRandom();

    @Update("update gerp_teast set status = 1 where id = #{id}")
    int readAndUpdate(int id);
}
