package com.twosmall.constellation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twosmall.constellation.entity.dao.TeastDao;
import com.twosmall.constellation.mapper.TeastMapper;
import com.twosmall.constellation.service.ITeastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gep
 * @since 2019-04-26
 */
@Service
public class TeastService extends ServiceImpl<TeastMapper, TeastDao> implements ITeastService {

    @Autowired
    private TeastMapper teastMapper;

    @Override
    public TeastDao getGreetings() {
//        QueryWrapper<TeastDao> query = new QueryWrapper<>();
//        query.eq("status",0);
        TeastDao teastDao = teastMapper.queryByRandom();
            teastMapper.readAndUpdate(teastDao.getId());
        return teastDao;
    }

    @Override
    public JSONObject saveGreetings(String content) {
        JSONObject result = new JSONObject();
        TeastDao dao = new TeastDao();
        dao.setContent(content);
        int insert = teastMapper.insert(dao);
        if (insert != 0) {
            result.put("flag", true);
        }else{
            result.put("flag", false);
        }
        return result;
    }
}

