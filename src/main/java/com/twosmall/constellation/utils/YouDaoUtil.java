package com.twosmall.constellation.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class YouDaoUtil {
    private static Logger logger = LoggerFactory.getLogger(YouDaoUtil.class);

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";
    private static final String TTS = "https://openapi.youdao.com/ttsapi";

    private static final String APP_KEY = "4ab841dab89e24aa";

    private static final String APP_SECRET = "SF5mrPG8JsoBseVEwWyWdSpz74TQ4Cfk";
    private static final String TTS_APP_KEY = "6a603aa67877fb96";
    private static final String TTS_APP_SECRET = "iQi8odZ5vjobDXkAJffCbQrdwRTKhyGI";
//    private static final String TTS_APP_KEY = "0d03587d76c0b974";
//    private static final String TTS_APP_SECRET = "GRyWXX2cOfrqju1ioYDrsaGUDXrhaw7N";

    public static void main(String[] args) throws IOException {

        ttsTest("absent");
//        extracted();
    }

    public static String ttsTest(String word) throws IOException {
        Map<String,String> params = new HashMap<String,String>();
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("langType", "en");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = TTS_APP_KEY + truncate(word) + salt + curtime + TTS_APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", TTS_APP_KEY);
        params.put("q", word);
        params.put("salt", salt);
        params.put("sign", sign);
        return requestForHttp(TTS,params);
    }
    private static void extracted() throws IOException {
        Map<String,String> params = new HashMap<String,String>();
        String q = "home";
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", "en");
        params.put("to", "zh-CHS");
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
//        params.put("vocabId","您的用户词表ID");
        /** 处理结果 */
        requestForHttp(YOUDAO_URL,params);
    }

    public static String requestForHttp(String url,Map<String,String> params) throws IOException {

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String,String>> it = params.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String,String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key,value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList,"UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try{
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            logger.info("Content-Type:" + contentType[0].getValue());
            if("audio/mp3".equals(contentType[0].getValue())){
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if(result != null){//合成成功
                    String word = params.get("q");
                    String file = "D:/word/"+word + ".mp3";
                    byte2File(result,file);
                    return file;
                }
            }else{
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity,"UTF-8");
                EntityUtils.consume(httpEntity);
                logger.info(json);
                System.out.println(json);
            }
        }finally {
            try{
                if(httpResponse!=null){
                    httpResponse.close();
                }
            }catch(IOException e){
                logger.info("## release resouce error ##" + e);
            }
        }
        return "";
    }

    /**
     * 生成加密字段
     */
    public static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     *
     * @param result 音频字节流
     * @param file 存储路径
     */
    private static void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        }catch (Exception e){
            logger.info(e.toString());
        }finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}
