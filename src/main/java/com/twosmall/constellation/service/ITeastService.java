package com.twosmall.constellation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.twosmall.constellation.entity.dao.TeastDao;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gep
 * @since 2019-04-26
 */
public interface ITeastService extends IService<TeastDao> {
    TeastDao getGreetings();
    JSONObject saveGreetings(String content);
}
