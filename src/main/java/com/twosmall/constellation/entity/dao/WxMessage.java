package com.twosmall.constellation.entity.dao;

import lombok.*;

/**
 * @author gep
 * @description 代码千万行, 注释第一行;编程不规范,同事两行泪;
 * @date 2019-04-26 18:04
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WxMessage {
    // 开发者微信号
    private String toUserName;
    // 发送方帐号（一个OpenID）
    private String fromUserName;
    // 消息创建时间 （整型）
    private long createTime;
    // 消息类型（text/image/location/link）
    private String msgType;
    // 消息id，64位整型
    private long msgId;
}
