package com.twosmall.constellation.controller;


import com.alibaba.fastjson.JSONObject;
import com.twosmall.constellation.entity.dao.TeastDao;
import com.twosmall.constellation.entity.dao.TextMessage;
import com.twosmall.constellation.entity.dao.WxMessage;
import com.twosmall.constellation.service.ITeastService;
import com.twosmall.constellation.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gep
 * @since 2019-04-26
 */
@Controller
@RequestMapping("/teast")
public class TeastController {

    private static final String RESP_MESSAGE_TYPE_TEXT = "text";
    @Autowired
    private ITeastService teastService;

    @RequestMapping("getGreetings")
    @ResponseBody
    public TeastDao getGreetings(){
        return teastService.getGreetings();
    }

    @RequestMapping("/get")
    public String getTeastIndex(){
        return "getTeast";
    }
    @RequestMapping("/save")
    public String index(){
        return "saveTeast";
    }

    @RequestMapping("saveGreetings")
    @ResponseBody
    public JSONObject saveGreetings(@NotNull @Valid @RequestParam(value = "content", required = false) String content){
        return teastService.saveGreetings(content);
    }

    @RequestMapping(value = "/weChat", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String receiveMessage(HttpServletRequest request){

        //1. 获取微信服务器发送的消息，转换成map对象
        Map<String, String> map = MessageUtil.parseXmlToMap(request);
        // 2. 获取详细的信息
        // 发送方帐号（open_id）
        String fromUserName = map.get("FromUserName");
        // 公众帐号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 消息内容
        String content = map.get("Content");
        // 消息id
        String msgId = map.get("MsgId");

        //3. 定义回复消息对象
        String respMsg = "";

        // 也可以用new，然后一个属性一个属性的set
        // 微信消息的基类
        //set属性值的时候，注意：ToUserName 和 FromUserName的值要反过来！是坑!是坑!是坑!
        WxMessage msg = WxMessage.builder().fromUserName(toUserName).toUserName(fromUserName).msgType(msgType).msgId(Long.parseLong(msgId))
                .createTime(System.currentTimeMillis()).build();

        if (RESP_MESSAGE_TYPE_TEXT.equals(msgType)){//文本消息
            //要回复的消息内容
            String resultContent = "";
            if ("222".equals(content)){
                TeastDao greetings = teastService.getGreetings();
                resultContent = greetings.getContent();
            }else {
                resultContent = "你说什么我听不懂~";
            }
            TextMessage textMessage = TextMessage.adapt(msg);
            textMessage.setContent(resultContent);
            respMsg = MessageUtil.parseMsgToXml(textMessage, TextMessage.class);
        }

        return respMsg;
    }
}

