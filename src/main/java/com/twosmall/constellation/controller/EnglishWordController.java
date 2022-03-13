package com.twosmall.constellation.controller;


import com.twosmall.constellation.entity.dao.TeastDao;
import com.twosmall.constellation.service.IEnglishWordService;
import com.twosmall.constellation.service.ITeastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author enzo
 * @since 2022-03-11
 */
@Controller
@RequestMapping("/englishWord")
public class EnglishWordController {


    @Autowired
    private IEnglishWordService englishWordService;

    @RequestMapping("generateAudio")
    @ResponseBody
    public TeastDao generateAudio(){
        return englishWordService.generateAudio();
    }

    @RequestMapping("passTheWord")
    @ResponseBody
    public TeastDao passTheWord(){
        return englishWordService.passTheWord();
    }

    @RequestMapping("addToNote")
    @ResponseBody
    public TeastDao addToNote(){
        return englishWordService.addToNote();
    }

}

