

package com.twosmall.constellation.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class OssUtil {

    //一天
    private static final long EXPIRD_SECOND = 24 * 60 * 60;

    //人脸图片默认尺寸
    public static final String FACE_SIZE_DEFAULT = "w_700,h_700";

    private static String ACCESS_KEY;

    private static String SECRET_KEY;

    private static String BUCK_NAME;

    private static String OSS_DOMAIN;
    static {
        BUCK_NAME = "sqwn-face";
        OSS_DOMAIN = "http://oss-cn-hangzhou.aliyuncs.com";
        ACCESS_KEY = "111";
//        ACCESS_KEY = "LTAI5t8Xjh8VWmXVQwD6ef3f";
//        SECRET_KEY = "xJRXO9didHuIH6xrXGsKctLJhZwI74";
        SECRET_KEY = "222";
    }
    private OSSClient ossClient;

    public OSSClient getOSSClientInstance() {
        if (ossClient == null) {
            synchronized (this) {
                if (ossClient == null) {
                    ossClient = new OSSClient(OSS_DOMAIN, ACCESS_KEY, SECRET_KEY);
                }
            }
        }
        return ossClient;
    }

    public static void main(String[] args) {
        String path = "d://word/realization.mp3";
        OssUtil ossUtil = new OssUtil();
        ossUtil.uploadToOss(path, null);
//
//        String url = ossUtil.getUrlByExpireUrlOrKey("https://sqwn-face.oss-cn-hangzhou.aliyuncs.com/0a0c0639-e2ca-49ba-a8b5-93099390aa2f.mp3?Expires=1647249385&OSSAccessKeyId=TMP.3KdXaKNm3NvprXRCNe7Q6GJRxXRguynFiuKPWm6P1HgWwENWxnYzP7ApRLEcweFiKfbFnqQYMrUNMKe5NyuRXDX6WKeGrX&Signature=x58Cybc2EHeY1PVk3snvHWMJR6I%3D",null);
//        System.out.println(url);
    }

    /**
     * 上传图片可以设置图片尺寸
     *
     * @param localFilePath 图片地址
     * @param resize        设置图片尺寸  w_700,h_700
     * @return
     */
    public String uploadToOss(String localFilePath, String resize) {
        //文件名
        String fileExtensionName = getExtensionName(localFilePath);
        InputStream inputStream = null;
        String url = null;
        try {
            inputStream = new FileInputStream(localFilePath);
            url = this.uploadToOss(inputStream, fileExtensionName, resize);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 上传图片可以设置图片尺寸
     *
     * @param bytes
     * @param fileExtensionName 扩展名称
     * @param resize            设置图片尺寸  w_700,h_700
     * @return
     */
    public String uploadToOss(byte[] bytes, String fileExtensionName, String resize) {
        //文件名
        InputStream inputStream = null;
        String url = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            url = this.uploadToOss(inputStream, fileExtensionName, resize);
            inputStream.close();
        } catch (IOException e) {
            log.error("oss图片下载异常：{}", e.getMessage());
        }
        return url;
    }

    /**
     * 上传图片地址
     *
     * @param inputStream
     * @param fileExtensionName
     * @param resize
     * @return
     */
    public String uploadToOss(InputStream inputStream, String fileExtensionName, String resize) {
        //文件名
        String key = UUID.randomUUID().toString() + "." + fileExtensionName;
        getOSSClientInstance().putObject(BUCK_NAME, key, inputStream);
        log.info("uploadToOss fileExtensionName:{} ,resize:{}, key:{}", fileExtensionName, resize, key);
        return this.getUrlByExpireUrlOrKey(key, resize);
    }

    /**
     * 根据key 或  url 获取新的地址
     *
     * @param key
     * @param resize
     * @return
     */
    public String getUrlByExpireUrlOrKey(String key, String resize) {
        log.info("key={}", key);
        if (StringUtils.isBlank(key)) return null;
        if (key.startsWith("http")) {
            key = getKey(key);
            if (StringUtils.isBlank(key)) {
                log.info("getUrlByExpireUrlOrKey 不支持的地址 url:{}", key);
                return null;
            }
        }
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(BUCK_NAME, key, HttpMethod.GET);
        Date expiration = new Date(System.currentTimeMillis() + EXPIRD_SECOND * 1000);
        req.setExpiration(expiration);
        if (StringUtils.isNotBlank(resize)) {
            String style = "image/resize,m_fixed," + resize;
            req.setProcess(style);
        }
        //获取临时加密的url
        URL url = getOSSClientInstance().generatePresignedUrl(req);
        log.info("getUrlByExpireUrlOrKey url:{}, key:{} ,resize:{}", url, key, resize);
        return JSON.toJSONString(url).replace("\"", "");
    }

    public String downloadFile(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) return null;
        try {
            URL url = new URL(imageUrl);
            String[] split = imageUrl.split("/");
            int i = split[3].indexOf("?");
            int j = split[3].indexOf(".");
            String fileExtensionName = "jpg";
            if (i > 0 && j > 0) {
                fileExtensionName = split[3].substring(j + 1, i);
            }
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            InputStream inStream = urlConnection.getInputStream();
            byte[] bytes = readInputStream(inStream);
            return uploadToOss(bytes, fileExtensionName, "false");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        byte[] bytes = outStream.toByteArray();
        outStream.close();
        return bytes;
    }

    /**
     * 获取扩展名称
     *
     * @param filename
     * @return
     */
    public String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(".");
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取图片key
     *
     * @param url
     * @return
     */
    public String getKey(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] split = url.split("/");
        if (split != null && split.length >= 4) {
            int i = split[3].indexOf("?");
            return split[3].substring(0, i);
        }
        return null;
    }

}




