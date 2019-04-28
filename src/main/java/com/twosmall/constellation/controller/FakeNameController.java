package com.twosmall.constellation.controller;

import com.github.javafaker.Faker;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author gep
 * @description 代码千万行, 注释第一行;编程不规范,同事两行泪;
 * @date 2019-02-27 10:08
 */

@Controller
public class FakeNameController {
    @RequestMapping("/fakeName")
    public String index(){
        return "fakeNameIndex";
    }

    @RequestMapping("getFakeName")
    @ResponseBody
    public JSONObject getFakeName(HttpServletRequest request){
        String fakeName = "";
        JSONObject object = new JSONObject();
        Faker faker = new Faker(new Locale("zh-CN"));
        fakeName = faker.name().fullName();
        object.put("name", fakeName);
        String ip = getIp(request);
        System.out.println(fakeName+"-->"+ip);
        return object;
    }
    public static String getIp(HttpServletRequest request){

        //代理进来，则透过防火墙获取真实IP地址

        String ip = request.getHeader("X-Forwarded-For");


        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_CLIENT_IP");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("HTTP_X_FORWARDED_FOR");

        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("X-Real-IP");

        }



//如果没有代理，则获取真实ip

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();

        }



        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;

    }
}
