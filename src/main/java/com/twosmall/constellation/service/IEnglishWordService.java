package com.twosmall.constellation.service;

import com.twosmall.constellation.entity.dao.EnglishWordDao;
import com.baomidou.mybatisplus.extension.service.IService;
import com.twosmall.constellation.entity.dao.TeastDao;

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

    TeastDao passTheWord();

    TeastDao addToNote();

}
