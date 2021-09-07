package com.twosmall.constellation.controller;

import com.twosmall.constellation.utils.RequestFortuneUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gep
 * @description 生辰
 * @date 2019/2/3 0003 10:21
 */
@Controller
public class BirthController {

    @RequestMapping("/birth")
    public String index() {
        return "birthIndex";
    }

    /**
     * 获取生辰
     *
     * @param request
     * @return
     */
    @RequestMapping("getBirth")
    @ResponseBody
    public JSONObject getFortuneByTypeAndName(HttpServletRequest request) {
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String day = request.getParameter("day");
        String hour = request.getParameter("hour");
        System.out.println(year);
        //getBirth
        String result = RequestFortuneUtil.getBirth(year, month, day, hour);
        result = result.replace("null", "\"\"");
        JSONObject object = JSONObject.fromObject(result);
        System.out.println(result);
        return object;
    }
}
