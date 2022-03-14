package com.twosmall.constellation.mapper;

import com.twosmall.constellation.entity.dao.EnglishWordDao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.twosmall.constellation.entity.dao.TeastDao;
import org.apache.ibatis.annotations.Select;
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

    @Select("select * from gerp_english_word where study_status = 0 limit 1")
    EnglishWordDao queryByRandom();

    @Update("update gerp_english_word set note_flag = 1 where id = #{id}")
    int addToNote(int id);

    @Update("update gerp_english_word set study_status = 1 where id = #{id}")
    int doneWord(int id);

}
