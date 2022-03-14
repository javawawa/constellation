package com.twosmall.constellation.service;

import javax.validation.constraints.NotNull;
import com.twosmall.constellation.entity.dao.EnglishWordDao;
import com.baomidou.mybatisplus.extension.service.IService;
import com.twosmall.constellation.entity.dao.TeastDao;

import javax.validation.Valid;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author enzo
 * @since 2022-03-11
 */
public interface IEnglishWordService extends IService<EnglishWordDao> {

    TeastDao generateAudio();

    int passTheWord(@NotNull @Valid Integer id);

    int addToNote(@NotNull @Valid Integer id);

    EnglishWordDao getOneWord();
}
