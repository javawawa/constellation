package com.twosmall.constellation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twosmall.constellation.entity.dao.EnglishWordDao;
import com.twosmall.constellation.entity.dao.TeastDao;
import com.twosmall.constellation.mapper.EnglishWordMapper;
import com.twosmall.constellation.service.IEnglishWordService;
import com.twosmall.constellation.utils.YouDaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author enzo
 * @since 2022-03-11
 */
@Service
public class EnglishWordService extends ServiceImpl<EnglishWordMapper, EnglishWordDao> implements IEnglishWordService {

    @Autowired
    private EnglishWordMapper englishWordMapper;

    @Override
    public TeastDao generateAudio() {
        QueryWrapper<EnglishWordDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("word", "/");
//        queryWrapper.isNull("audio_path_en");
        List<EnglishWordDao> englishWords = englishWordMapper.selectList(queryWrapper);
        for (EnglishWordDao englishWord : englishWords) {
            String word = englishWord.getWord();
            String[] split = word.split("/");
            word = split[0];
            System.out.println(word);
            try {
                String url = YouDaoUtil.ttsTest(word);
                englishWordMapper.updateUrl("/mnt/word/" + word + ".mp3", englishWord.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int passTheWord(@NotNull @Valid Integer id) {
        return englishWordMapper.doneWord(id);
    }

    @Override
    public int addToNote(@NotNull @Valid Integer id) {
        return englishWordMapper.addToNote(id);
    }

    @Override
    public TeastDao getOneWord() {
        return englishWordMapper.queryByRandom();
    }
}
