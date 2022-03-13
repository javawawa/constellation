package com.twosmall.constellation.mapper;

import com.twosmall.constellation.entity.dao.EnglishWordDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author enzo
 * @since 2022-03-11
 */
public interface EnglishWordMapper extends BaseMapper<EnglishWordDao> {

    @Update("update gerp_english_word set audio_path_en = #{url} where id = #{id}")
    int updateUrl(String url ,int id);

}
