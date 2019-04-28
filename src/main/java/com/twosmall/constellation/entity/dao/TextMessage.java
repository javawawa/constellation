package com.twosmall.constellation.entity.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * @author gep
 * @description 代码千万行, 注释第一行;编程不规范,同事两行泪;
 * @date 2019-04-26 18:07
 */
@Getter
@Setter
@NoArgsConstructor
public class TextMessage extends WxMessage {
    private String Content;// 文本消息内容

    //用来把基类的属性值复制给子类
    public static TextMessage adapt(WxMessage msg){
        TextMessage textMessage = new TextMessage();
        BeanUtils.copyProperties(msg, textMessage);
        return textMessage;
    }
}
