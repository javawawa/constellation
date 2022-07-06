package com.twosmall.constellation.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.dtflys.forest.Forest;
import com.twosmall.constellation.entity.param.GuGuBirdCommonParam;
import com.twosmall.constellation.entity.param.PrintTableParam;
import com.twosmall.constellation.service.GuGuBirdExtendsService;
import com.twosmall.constellation.service.GuGuBirdService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GuGuBirdExtendsServiceImpl implements GuGuBirdExtendsService {

    public void printPaper(String printString) {
        GuGuBirdService myClient = Forest.client(GuGuBirdService.class);
        GuGuBirdCommonParam param = new GuGuBirdCommonParam();
        String encode = Base64.encode(printString, "GBK");
        String now = DateUtil.now();
        String result = myClient.printPaper(param.getAccessKey(), now, param.getDeviceCode(), "T:" + encode, param.getUserId());
        System.out.println(result);
    }

    public static void bind() {
        GuGuBirdService myClient = Forest.client(GuGuBirdService.class);
        GuGuBirdCommonParam param = new GuGuBirdCommonParam();
        String now = DateUtil.now();
        String result = myClient.accountBind(param.getAccessKey(), now, param.getDeviceCode(), param.getUserIdString());
        System.out.println(result);
    }

    public static void main(String[] args) {
        PrintTableParam param = new PrintTableParam();
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>(1);
        map1.put("鱼香肉丝", "1");
        list.add(map1);
        Map<String, String> map2 = new HashMap<>(1);
        map2.put("排骨烩酸菜", "2");
        list.add(map2);
        Map<String, String> map3 = new HashMap<>(1);
        map3.put("干锅手撕包心菜", "1");
        list.add(map3);
        param.setMenus(list);
        param.setOrderNumber("202206021122091112");
        param.setTableNumber("二楼雅间一");
//        String result = printTable(param);
//        System.out.println(result);
//        bind();
//        printPaper(result);
//        printPaper(result);
        //汉字16个 -32个
//        String encode = Base64.encode(printString);
//        System.out.println(encode);
//        String decode = Base64.decodeStr("d2VsY29tZSB0byB5b3U=");
//        System.out.println(decode);
    }

    @Override
    public String printTable(PrintTableParam param) {

        List<Map<String, String>> list = param.getMenus();
        int charLength = 25;
        StringBuilder stringBuilder = new StringBuilder();
        printLine(stringBuilder);
        stringBuilder.append("           老席私房菜          ").append("\n");
        printLine(stringBuilder);
        stringBuilder.append("  桌号： ");
        stringBuilder.append(param.getTableNumber()).append("\n");
        stringBuilder.append("  订单号： ");
        stringBuilder.append(param.getOrderNumber()).append("\n");
        String formatDateTime = DateUtil.formatDateTime(new Date());
        stringBuilder.append("  时间：");
        stringBuilder.append(formatDateTime);
        stringBuilder.append("\n");
        printLine(stringBuilder);
        stringBuilder.append("  菜品名称                数量  \n");
        printLine(stringBuilder);
        for (Map<String, String> map : list) {
            for (String key : map.keySet()) {
                int keyLength = key.length() * 2;
                int valueLength = map.get(key).length();
                if (valueLength > 1) {
                    charLength += 1;
                }
                stringBuilder.append("* ");
                stringBuilder.append(key);
                int keyCount = charLength - keyLength;
                for (int i = 0; i < keyCount; i++) {
                    stringBuilder.append(" ");
                }
                stringBuilder.append("X");
                stringBuilder.append(" ");
                stringBuilder.append(map.get(key));
                stringBuilder.append(" *");
                stringBuilder.append("\n");
            }
        }
        printLine(stringBuilder);
        printPaper(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static void printLine(StringBuilder stringBuilder) {
        int byteLength = 32;
        for (int i = 0; i < byteLength; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n");
    }
}
