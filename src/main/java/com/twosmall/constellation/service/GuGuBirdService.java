package com.twosmall.constellation.service;

import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.annotation.Var;

/**
 * @author enzo
 * @date 2021/11/30 3:37 下午
 * @description 咕咕机相关接口
 */
public interface GuGuBirdService {

    /**
     * 绑定账号
     *
     * @param accessKey  ak 483391cae8b94b358e25c285e4ba7616
     * @param date       时间戳
     * @param deviceCode 1663511ebe05bd9c
     * @param userIdString     12121233
     * @return result
     */
    @Request("http://open.memobird.cn/home/setuserbind?ak={accessKey}&timestamp={date}&memobirdID={deviceCode}&useridentifying={userIdString}")
    String accountBind(@Var("accessKey") String accessKey, @Var("date") String date, @Var("deviceCode") String deviceCode, @Var("userIdString") String userIdString);

    /**
     * 打印纸条
     *
     * @param accessKey    483391cae8b94b358e25c285e4ba7616
     * @param date         时间戳
     * @param deviceCode   1663511ebe05bd9c
     * @param printContent 文本内容(汉字要GBK 格式的Base64)/图片(图片为单色点位图)的Base64 编码值
     *                     T:文本
     *                     P:图片
     * @param userId       5538262
     * @return
     */
    @Post("http://open.memobird.cn/home/printpaper?ak={accessKey}&timestamp={date}&memobirdID={deviceCode}&printcontent={printContent}&userID={userId}")
    String printPaper(@Var("accessKey") String accessKey, @Var("date") String date, @Var("deviceCode") String deviceCode, @Var("printContent") String printContent, @Var("userId") String userId);

}
