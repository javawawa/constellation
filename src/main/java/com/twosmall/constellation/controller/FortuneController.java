package com.twosmall.constellation.controller;

import com.twosmall.constellation.utils.RequestFortuneUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gep
 * @description
 * @date 2019/2/3 0003 10:21
 */
@Controller
public class FortuneController {

    @RequestMapping("/index")
    public String index(){
        System.out.println("index");
        return "fortuneIndex";
    }

    /**
     * 获取运势
     * @param request
     * @return
     */
    @RequestMapping("getFortune")
    @ResponseBody
    @CrossOrigin()
    public JSONObject getFortuneByTypeAndName(HttpServletRequest request){
        String constellation = request.getParameter("constellation");
        String fortuneType = request.getParameter("fortuneType");
        String result = RequestFortuneUtil.getFortune(constellation,fortuneType);
        result = result.replace("null", "\"\"");
        JSONObject object = JSONObject.fromObject(result);
        System.out.println("Hello World");
        return object;
    }
}
