package com.twosmall.constellation.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @author gep
 * @description
 * @date 2019/2/3 0003 10:22
 */
public class RequestFortuneUtil {
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static final String WEATHER_URL = "http://apis.juhe.cn/simpleWeather/query";
    public static final String LIFE_INDEX_URL = "http://apis.juhe.cn/simpleWeather/life";
    public static final String FORTUNE_URL = "http://web.juhe.cn:8080/constellation/getAll";
    public static final String BIRTH_URL = "http://apis.juhe.cn/birthEight/query";
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    /**
     * AK
     */
    public static final String FORTUNE_APP_KEY = "ccf94783e09b4b415593796641295ff8";
    public static final String WEATHER_APP_KEY = "4b540b603614fe840f2e2eec86fa6227";
    public static final String BIRTH_APP_KEY = "44c4b3b47a6b3737e0c407c9dde4f3dc";

    /**
     * 运势查询
     */
    public static String getFortune(String constellation, String fortuneType) {
        String result = null;
        Map params = new HashMap();
        params.put("key", FORTUNE_APP_KEY);
        params.put("consName", constellation);
        params.put("type", fortuneType);

        try {
            result = net(FORTUNE_URL, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.toString());
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取天气
     *
     * @param cityname 城市
     * @return result
     */
    public static String getWeather(String cityname) {
        String result = null;
        Map params = new HashMap();
        params.put("key", WEATHER_APP_KEY);
        params.put("city", cityname);
        try {
            result = net(WEATHER_URL, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.toString());
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取指数
     *
     * @param cityname
     * @return
     */
    public static String getLifeIndex(String cityname) {
        String result = null;
        Map params = new HashMap();
        params.put("key", WEATHER_APP_KEY);
        params.put("city", cityname);
        try {
            result = net(LIFE_INDEX_URL, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.toString());
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 八字查询
     */
    public static String getBirth(String year,String month,String day,String hour) {
        String result = null;
        Map params = new HashMap();
        params.put("key", BIRTH_APP_KEY);
        params.put("year", year);
        params.put("month", month);
        params.put("day", day);
        params.put("hour", hour);

        try {
            result = net(BIRTH_URL, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if (object.getInt("error_code") == 0) {
                System.out.println(object.toString());
            } else {
                System.out.println(object.get("error_code") + ":" + object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
//        getLifeIndex("杭州");
        getBirth("1993","12","3","11");
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlEncode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlEncode(params));
                } catch (Exception e) {
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlEncode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
