package com.twosmall.constellation.entity.param;

import lombok.Data;

/**
 * @author enzo
 * @date 2021/11/30 4:39 下午
 * @description 咕咕机公共参数
 */
@Data
public class GuGuBirdCommonParam {

    private String accessKey = "8d303499677f4fe7a7e32e1ef7faedcd";
    private String date;
    private String deviceCode = "dab60e55afbb453a";
    /**
     * 绑定后返回的user_id
     */
    private String userId = "5538244";
    /**
     * 自定义用户id 绑定用
     */
    private String userIdString = "12121233";
}
