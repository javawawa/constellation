package com.twosmall.constellation.entity.entity;

import com.twosmall.constellation.utils.RequestFortuneUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.util.ArrayList;

@Slf4j
public class SendTextMessage {
    //机器人消息token
    public static String WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=6816c3e9f029b3b81138bc9b79024e1f7a64e44fedeba1e751d86b63eadc0a76";

    private static RobotClient robot = new RobotClient();

    /**
     * 发送普通文本消息
     *
     * @param message
     * @return
     */
    public static SendResult send(String message) {
        TextMessage textMessage = new TextMessage(message);
        SendResult sendResult = null;
        try {
            sendResult = robot.send(WEBHOOK_TOKEN, textMessage);
        } catch (Exception e) {
            log.error("===> send robot message error:", sendResult);
        }
        return sendResult;
    }

    public static void sendMarkdownMessage(String title, String text){
        try {
            MarkdownMessage message = new MarkdownMessage();
            message.setTitle(title);
            message.add(text);
            robot.send(WEBHOOK_TOKEN,message);
        } catch (Exception e) {
            log.error("===> send robot message error:");
        }
    }

    /**
     * 发送文本消息 可以@部分人
     *
     * @param message
     * @param atMobiles 要@人的电话号码 ArrayList<String>
     * @return
     */
    public static SendResult sendWithAt(String message, ArrayList<String> atMobiles) {
        TextMessage textMessage = new TextMessage(message);
        SendResult sendResult = null;
        textMessage.setAtMobiles(atMobiles);
        try {
            sendResult = robot.send(WEBHOOK_TOKEN, textMessage);
        } catch (Exception e) {
            log.error("===> send robot message atPeople error:", sendResult);
        }
        return sendResult;
    }

    /**
     * 发送文本消息 并@所有人
     *
     * @param message
     * @return
     */
    public static SendResult sendWithAtAll(String message) {
        TextMessage textMessage = new TextMessage(message);
        SendResult sendResult = null;
        textMessage.setIsAtAll(true);
        try {
            sendResult = robot.send(WEBHOOK_TOKEN, textMessage);
        } catch (Exception e) {
            log.error("===> send robot message atAll error:", sendResult);
        }
        return sendResult;
    }



    public static void main(String[] args) {
        String result = RequestFortuneUtil.getWeather("杭州");
//        String result = "{\"resultcode\":\"200\",\"reason\":\"successed!\",\"result\":{\"sk\":{\"temp\":\"19\",\"wind_direction\":\"西南风\",\"wind_strength\":\"1级\",\"humidity\":\"36%\",\"time\":\"16:22\"},\"today\":{\"temperature\":\"10℃~19℃\",\"weather\":\"多云\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"01\"},\"wind\":\"东风3-5级\",\"week\":\"星期四\",\"city\":\"杭州\",\"date_y\":\"2020年04月23日\",\"dressing_index\":\"较舒适\",\"dressing_advice\":\"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。\",\"uv_index\":\"弱\",\"comfort_index\":\"\",\"wash_index\":\"较适宜\",\"travel_index\":\"较适宜\",\"exercise_index\":\"较适宜\",\"drying_index\":\"\"},\"future\":{\"day_20200423\":{\"temperature\":\"10℃~19℃\",\"weather\":\"多云\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"01\"},\"wind\":\"东风3-5级\",\"week\":\"星期四\",\"date\":\"20200423\"},\"day_20200424\":{\"temperature\":\"12℃~23℃\",\"weather\":\"多云\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"01\"},\"wind\":\"持续无风向微风\",\"week\":\"星期五\",\"date\":\"20200424\"},\"day_20200425\":{\"temperature\":\"13℃~24℃\",\"weather\":\"阴\",\"weather_id\":{\"fa\":\"02\",\"fb\":\"02\"},\"wind\":\"西南风4-5级\",\"week\":\"星期六\",\"date\":\"20200425\"},\"day_20200426\":{\"temperature\":\"12℃~24℃\",\"weather\":\"阴转多云\",\"weather_id\":{\"fa\":\"02\",\"fb\":\"01\"},\"wind\":\"持续无风向微风\",\"week\":\"星期日\",\"date\":\"20200426\"},\"day_20200427\":{\"temperature\":\"8℃~24℃\",\"weather\":\"多云转晴\",\"weather_id\":{\"fa\":\"01\",\"fb\":\"00\"},\"wind\":\"北风5-6级\",\"week\":\"星期一\",\"date\":\"20200427\"},\"day_20200428\":{\"temperature\":\"13℃~24℃\",\"weather\":\"阴\",\"weather_id\":{\"fa\":\"02\",\"fb\":\"02\"},\"wind\":\"西南风4-5级\",\"week\":\"星期二\",\"date\":\"20200428\"},\"day_20200429\":{\"temperature\":\"12℃~24℃\",\"weather\":\"阴转多云\",\"weather_id\":{\"fa\":\"02\",\"fb\":\"01\"},\"wind\":\"持续无风向微风\",\"week\":\"星期三\",\"date\":\"20200429\"}}},\"error_code\":0}";
        result = result.replace("null", "\"\"");
        JSONObject weatherJson = JSONObject.fromObject(result);
        String fortuneResult = RequestFortuneUtil.getFortune("处女座", "today");
        fortuneResult = fortuneResult.replace("null", "\"\"");
        JSONObject fortuneJson = JSONObject.fromObject(fortuneResult);
        JSONObject weather = weatherJson.getJSONObject("result");
        JSONObject sk = weather.getJSONObject("sk");
        JSONObject today = weather.getJSONObject("today");
        MarkdownMessage markdownMessage = new MarkdownMessage();
        markdownMessage.setTitle("杭州天气");
        String wjson = "#### "+today.get("city")+"天气 \n" +
                today.get("date_y")+" "+today.get("week")+" \n" +
                "\n"+
                "当前温度: "+sk.get("temp")+"°"+sk.get("wind_direction")+" "+sk.get("wind_strength")+ " 相对湿度"+sk.get("humidity")+"\n" +
                "\n"+
                "今日温度: "+today.get("temperature")+today.get("weather")+today.get("wind")+"\n" +
                "\n"+
                "> - 穿衣指数："+today.get("dressing_index")+"\n" +
                "> - 穿衣建议："+today.get("dressing_advice")+"\n" +
                "> - 紫外线强度："+today.get("uv_index")+"\n" +
                "> - 舒适度指数："+today.get("comfort_index")+"\n" +
                "> - 洗车指数："+today.get("wash_index")+"\n" +
                "> - 晨练指数："+today.get("exercise_index")+"\n" +
                "> - 干燥指数："+today.get("drying_index")+"\n" +
                "\n"+
                "今日运势: "+fortuneJson.get("name")+" \n"+
                "\n"+
                "> - 综合指数："+fortuneJson.get("all")+"\n" +
                "> - 健康指数："+fortuneJson.get("health")+"\n" +
                "> - 爱情指数："+fortuneJson.get("love")+"\n" +
                "> - 财运指数："+fortuneJson.get("money")+"\n" +
                "> - 工作指数："+fortuneJson.get("work")+"\n" +
                "> - 幸运数字："+fortuneJson.get("number")+"\n" +
                "> - 幸运色："+fortuneJson.get("color")+"\n" +
                "> - 今日概述："+fortuneJson.get("summary")+"\n" +
                "\n"+
                "[今日份新闻](https://36kr.com/)";
        System.out.println(wjson);
//        send(wjson);
        sendMarkdownMessage("杭州天气",wjson);
    }
}
