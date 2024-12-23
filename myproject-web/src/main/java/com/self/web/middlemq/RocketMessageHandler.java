package com.self.web.middlemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class RocketMessageHandler  implements MessageListenerConcurrently {
    public void setOpenMessageListener(OpenMessageListener openMessageListener) {
        this.openMessageListener = openMessageListener;
    }

    private OpenMessageListener openMessageListener;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

        for (MessageExt messageExt : msgs) {
            log.debug("received msg: {}", messageExt);
            try {
                long now = System.currentTimeMillis();
                String body = new String(messageExt.getBody(), "UTF-8");
                Message message = Message.builder()
                        .key(messageExt.getMsgId())
                        .topic(messageExt.getTopic())
                        .mqType("ROCKETMQ")
                        .body(body)
                        .build();
                openMessageListener.onMessage(message);
            } catch (Exception e) {
                log.warn("consume message failed. messageExt:{}", messageExt, e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
