package com.twosmall.constellation.utils;

import com.thoughtworks.xstream.XStream;
import com.twosmall.constellation.entity.dao.WxMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gep
 * @description 代码千万行, 注释第一行;编程不规范,同事两行泪;
 * @date 2019-04-26 18:10
 */
public class MessageUtil {
    /**
     *  接收request对象，读取xml内容，转换成map对象
     * @param request
     * @return
     */
    public static Map<String, String> parseXmlToMap(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }finally{
            try {
                if (null != ins){
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将消息转换成xml格式的字符串
     * @param msg 各种信息类，如文本信息类，图片信息类，音频信息类等。（都是WxMessage的子类）
     * @param child 用来确定到底是哪一种子类
     * @return
     */
    public static String parseMsgToXml(WxMessage msg, Class child){
        XStream xstream = new XStream();
        xstream.alias("xml", child);
        return xstream.toXML(msg);
    }
}
