package com.self.web.middlemq;

import lombok.Builder;
import lombok.Data;

/**
 * @author wanghongen
 * 2021/2/26
 */
@Data
@Builder
public class Message {
    /**
     * 消息key
     */
    private String key;
    /**
     * 消息ID
     */
    private String messageId;
    /**
     * 消息topic
     */
    private String topic;
    /**
     * 消息体
     */
    private String body;
    /**
     * mq类型
     */
    private String mqType;

}
