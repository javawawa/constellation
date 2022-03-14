package com.twosmall.constellation.controller;


import com.twosmall.constellation.entity.dao.TeastDao;
import com.twosmall.constellation.service.IEnglishWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @RequestMapping("getOneWord")
    @ResponseBody
    public TeastDao getOneWord(){
        return englishWordService.getOneWord();
    }

    @RequestMapping("passTheWord")
    @ResponseBody
    public int passTheWord(@NotNull @Valid @RequestParam(value = "id", required = false) Integer id){
        return englishWordService.passTheWord(id);
    }

    @RequestMapping("addToNote")
    @ResponseBody
    public int addToNote(@NotNull @Valid @RequestParam(value = "id", required = false) Integer id){
        return englishWordService.addToNote(id);
    }

}

