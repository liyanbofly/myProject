package com.self.web.middlemq;

/**
 * 消息监听者
 *
 * @author wanghongen
 * 2019/12/3
 */
public interface OpenMessageListener {
    /**
     * 消息处理
     *
     * @param message 消息
     */
    void onMessage(Message message);
}
