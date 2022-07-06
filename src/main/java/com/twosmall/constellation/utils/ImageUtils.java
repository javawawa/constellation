package com.twosmall.constellation.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Slf4j
@Component
public class ImageUtils {

    @Autowired
    private OssUtil ossUtil;

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * 根据字符串生成对应的二维码图片png
     * 大小:200*200
     * <p>
     * content：要转换的内容
     * path：生成的二维码图片的绝对路径
     * filename: 生成的文件名
     */
    public static BufferedImage buildQuickMark(String content) throws Exception {
        try {
            if (StringUtils.isBlank(content)) {
                return null;
            }
            BitMatrix byteMatrix = new MultiFormatWriter().encode(new String(content.getBytes(), "iso-8859-1"),
                    BarcodeFormat.QR_CODE, 200, 200);
            return toBufferedImage(byteMatrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    /**
     * 生成图片到本地
     *
     * @param buffImg
     * @param savePath
     */
    public static void generateSaveFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            File outFile = new File(savePath);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            ImageIO.write(buffImg, savePath.substring(temp), outFile);
            System.out.println("ImageIO write...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage coverImage(BufferedImage baseBufferedImage, BufferedImage coverBufferedImage, int x, int y,
                                           int width, int height) throws Exception {
        // 创建Graphics2D对象，⽤在底图对象上绘图
        Graphics2D g2d = baseBufferedImage.createGraphics();
        // 绘制
        g2d.drawImage(coverBufferedImage, x, y, width, height, null);
        g2d.dispose();
        // 释放图形上下⽂使⽤的系统资源
        return baseBufferedImage;
    }

    /**
     * 通过url获取网络图片
     *
     * @param url
     * @return
     */
    public static BufferedImage getImageByUrl(String url) {
        HttpURLConnection httpUrl = null;
        BufferedImage read = null;
        try {
            URL conurl = new URL(url);
            httpUrl = (HttpURLConnection) conurl.openConnection();
            httpUrl.connect();
            read = ImageIO.read(httpUrl.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpUrl.disconnect();
        }
        return read;
    }

    private static BufferedImage drawText(BufferedImage bi, String key, final int startX, final int startY) {

        try {
            Color color = new Color(255, 255, 255);
//            InputStream fi = ImageUtil.class.getClassLoader().getResourceAsStream("font/pingfang.ttf");
//            String filepath =ImageUtil.class.getClassLoader().getResource("font/pingfang.ttf").getPath();
            String filepath = "D:\\back\\font\\pingfang.ttf";
            File file = new File(filepath);
            FileInputStream fi = new FileInputStream(file);
            BufferedInputStream fb = new BufferedInputStream(fi);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fb);
            font = font.deriveFont(Font.BOLD, 20);
            Graphics2D graphics = (Graphics2D) bi.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.setFont(font);
            graphics.setColor(color);
            graphics.drawString(key, startX, startY);
            graphics.dispose();
        } catch (FontFormatException | IOException ignored) {
            return null;
        }
        return bi;
    }

    /**
     * 生成桌面二维码
     *
     * @param orderUrl  点餐地址
     * @param tableName 桌名
     * @return 二维码地址
     */
    public static String createTableImage(String orderUrl, String tableName) {
        try {
            //生成的二维码
            BufferedImage qrCodeImageBuffer = buildQuickMark(orderUrl);
            OssUtil ossUtil = new OssUtil();
            //二维码原始图片模板
            String netUrl = "http://sqwn-face.oss-cn-hangzhou.aliyuncs.com/2165cb9e-778e-4a8b-accd-9f7d52aae039.jpg?Expires=1654915627&OSSAccessKeyId=LTAI5tDFPKfWR7PGXdFpw8m7&Signature=RXpKND3UgRTgUEFe7QoGBVNhLkc%3D";
            String urlByExpireUrlOrKey = ossUtil.getUrlByExpireUrlOrKey(netUrl, null);
            BufferedImage sourceImage = getImageByUrl(urlByExpireUrlOrKey);
            BufferedImage mergeImageBuffer = coverImage(sourceImage, qrCodeImageBuffer, 134, 226, 120, 120);
            Color color = new Color(255, 255, 255);
            String filepath = ImageUtils.class.getClassLoader().getResource("font/pingfang.ttf").getPath();
            log.info("filepath:{}", filepath);
            InputStream fi = ImageUtils.class.getClassLoader().getResourceAsStream("font/pingfang.ttf");
            BufferedInputStream fb = new BufferedInputStream(Objects.requireNonNull(fi));
            Font font = Font.createFont(Font.TRUETYPE_FONT, fb);
            font = font.deriveFont(Font.BOLD, 20);
            Graphics2D graphics = (Graphics2D) mergeImageBuffer.getGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.setFont(font);
            graphics.setColor(color);
            int width = mergeImageBuffer.getWidth();
            int length = tableName.length();
            int startX = (width - length * 20) / 2;
            graphics.drawString(tableName, startX, 390);
            graphics.dispose();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(mergeImageBuffer, "jpg", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return ossUtil.uploadToOss(input, "jpg", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        extracted();
        testText();
    }

    public static void testText() {
        String mergePath = "D://mergeImage.jpg";
        String savePath = "D://text.jpg";
        String key = "雅间二十号桌";
        File outFile = new File(mergePath);
        try {
            BufferedImage read = ImageIO.read(outFile);
            int width = read.getWidth();
            int length = key.length();
            int startX = (width - length * 20) / 2;
            BufferedImage bufferedImage = drawText(read, key, startX, 390);
//            generateSaveFile(bufferedImage, savePath);
            OssUtil ossUtil = new OssUtil();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            String url = ossUtil.uploadToOss(input, "jpg", null);
            System.out.println(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extracted() {
        String url = "http://10.0.132.59:7777/system?id=1";


        String mergePath = "D://mergeImage.jpg";
        String netUrl = "http://sqwn-face.oss-cn-hangzhou.aliyuncs.com/2165cb9e-778e-4a8b-accd-9f7d52aae039.jpg?Expires=1654915627&OSSAccessKeyId=LTAI5tDFPKfWR7PGXdFpw8m7&Signature=RXpKND3UgRTgUEFe7QoGBVNhLkc%3D";
        try {
            BufferedImage bufferedImage = buildQuickMark(url);
            OssUtil ossUtil = new OssUtil();
            String urlByExpireUrlOrKey = ossUtil.getUrlByExpireUrlOrKey(netUrl, null);
            BufferedImage sourceImage = getImageByUrl(urlByExpireUrlOrKey);
            BufferedImage bufferedImage1 = coverImage(sourceImage, bufferedImage, 134, 226, 120, 120);
            generateSaveFile(bufferedImage1, mergePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
