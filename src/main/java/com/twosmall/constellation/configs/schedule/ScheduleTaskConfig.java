package com.twosmall.constellation.configs.schedule;

import com.twosmall.constellation.entity.entity.MarkdownMessage;
import com.twosmall.constellation.entity.entity.SendTextMessage;
import com.twosmall.constellation.utils.RequestFortuneUtil;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;

/**
 * 定时任务
 *
 * @author gep
 * @date 2020年4月21日 14:47:35
 */
@Configuration
@EnableScheduling
public class ScheduleTaskConfig {


    @Scheduled(cron = "0 30 7 * * ? ")
    private void weatherSend() {
        SendTextMessage.sendMarkdownMessage("杭州天气", generateMarkDownText());
    }

//    private static String generateMarkDownText() {
//        String result = RequestFortuneUtil.getWeather("杭州");
//        result = result.replace("null", "\"\"");
//        JSONObject weatherJson = JSONObject.fromObject(result);
////        String fortuneResult = RequestFortuneUtil.getFortune("处女座", "today");
////        fortuneResult = fortuneResult.replace("null", "\"\"");
////        JSONObject fortuneJson = JSONObject.fromObject(fortuneResult);
//        JSONObject weather = weatherJson.getJSONObject("result");
//        JSONObject sk = weather.getJSONObject("sk");
//        JSONObject today = weather.getJSONObject("today");
//        MarkdownMessage markdownMessage = new MarkdownMessage();
//        markdownMessage.setTitle("杭州天气");
//        String markDown = "#### " + today.get("city") + "天气 \n" +
//                today.get("date_y") + " " + today.get("week") + " \n" +
//                "\n" +
//                "当前温度: " + sk.get("temp") + "°" + sk.get("wind_direction") + " " + sk.get("wind_strength") + " 相对湿度" + sk.get("humidity") + "\n" +
//                "\n" +
//                "今日温度: " + today.get("temperature") + today.get("weather") + today.get("wind") + "\n" +
//                "\n" +
//                "> - 穿衣指数：" + today.get("dressing_index") + "\n" +
//                "> - 穿衣建议：" + today.get("dressing_advice") + "\n" +
//                "> - 紫外线强度：" + today.get("uv_index") + "\n" +
//                "> - 舒适度指数：" + today.get("comfort_index") + "\n" +
//                "> - 洗车指数：" + today.get("wash_index") + "\n" +
//                "> - 晨练指数：" + today.get("exercise_index") + "\n" +
//                "> - 干燥指数：" + today.get("drying_index") + "\n" +
////                "\n" +
////                "今日运势: " + fortuneJson.get("name") + " \n" +
////                "\n" +
////                "> - 综合指数：" + fortuneJson.get("all") + "\n" +
////                "> - 健康指数：" + fortuneJson.get("health") + "\n" +
////                "> - 爱情指数：" + fortuneJson.get("love") + "\n" +
////                "> - 财运指数：" + fortuneJson.get("money") + "\n" +
////                "> - 工作指数：" + fortuneJson.get("work") + "\n" +
////                "> - 幸运数字：" + fortuneJson.get("number") + "\n" +
////                "> - 幸运色：" + fortuneJson.get("color") + "\n" +
////                "> - 今日概述：" + fortuneJson.get("summary") + "\n" +
//                "\n" +
//                "[今日热榜](https://tophub.today/)";
//        return markDown;
//    }

    public static String generateMarkDownText() {
        String result = RequestFortuneUtil.getWeather("杭州");
        result = result.replace("null", "\"\"");
        JSONObject weatherJson = JSONObject.fromObject(result);
        JSONObject weatherResult = weatherJson.getJSONObject("result");
        JSONObject realtime = weatherResult.getJSONObject("realtime");
        String lifeIndex = RequestFortuneUtil.getLifeIndex("杭州");
        lifeIndex = lifeIndex.replace("null", "\"\"");
        JSONObject lifeJson = JSONObject.fromObject(lifeIndex);
        JSONObject lifeResult = lifeJson.getJSONObject("result");
        JSONObject life = lifeResult.getJSONObject("life");
        JSONObject kongtiao = life.getJSONObject("kongtiao");
        JSONObject chuanyi = life.getJSONObject("chuanyi");
        JSONObject ziwaixian = life.getJSONObject("ziwaixian");
        JSONObject shushidu = life.getJSONObject("shushidu");
        JSONObject xiche = life.getJSONObject("xiche");
        JSONObject yundong = life.getJSONObject("yundong");
        JSONObject guomin = life.getJSONObject("guomin");
        LocalDate date = LocalDate.now();
        String markDown = "#### " + weatherResult.get("city") + "天气 \n" +
                date+ " " + date.getDayOfWeek() + " \n" +
                "\n" +
                "当前温度: " + realtime.get("temperature") + "° " + realtime.get("direct") + " " + realtime.get("power") + "\n" +
                "\n" +
                "相对湿度: " + realtime.get("humidity") + "% 空气质量 " + realtime.get("aqi") + "\n" +
                "\n" +
                "> - 空调开启：" + kongtiao.get("v") + "。 " + kongtiao.get("des") + "\n" +
                "> - 穿衣建议：" + chuanyi.get("v") + "。 " + chuanyi.get("des") + "\n" +
                "> - 紫外线强度：" + ziwaixian.get("v") + "。 " + ziwaixian.get("des") + "\n" +
                "> - 舒适度指数：" + shushidu.get("v") + "。 " + shushidu.get("des") + "\n" +
                "> - 洗车指数：" + xiche.get("v") + "。 " + xiche.get("des") + "\n" +
                "> - 晨练指数：" + yundong.get("v") + "。 " + yundong.get("des") + "\n" +
                "> - 过敏指数：" + guomin.get("v") + "。 " + guomin.get("des") + "\n" +
//                "\n" +
//                "今日运势: " + fortuneJson.get("name") + " \n" +
//                "\n" +
//                "> - 综合指数：" + fortuneJson.get("all") + "\n" +
//                "> - 健康指数：" + fortuneJson.get("health") + "\n" +
//                "> - 爱情指数：" + fortuneJson.get("love") + "\n" +
//                "> - 财运指数：" + fortuneJson.get("money") + "\n" +
//                "> - 工作指数：" + fortuneJson.get("work") + "\n" +
//                "> - 幸运数字：" + fortuneJson.get("number") + "\n" +
//                "> - 幸运色：" + fortuneJson.get("color") + "\n" +
//                "> - 今日概述：" + fortuneJson.get("summary") + "\n" +
                "\n" +
                "[今日热榜](https://tophub.today/)";
        return markDown;
    }

    public static void main(String[] args) {
        System.out.println(generateMarkDownText());
    }
}
