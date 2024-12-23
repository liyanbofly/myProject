package com.self.web.middlemq.selfConsumer;

import com.self.web.middlemq.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 监听RocketMq消息
 *
 */
@Component
@Slf4j
public class RocketConsumerListenerSelf implements MessageListenerConcurrently {

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        for (MessageExt messageExt : msgs) {
            log.debug("received msg: {}", messageExt);
            System.out.println("messageExt = " + messageExt);
            try {
                long now = System.currentTimeMillis();
                String body = new String(messageExt.getBody(), "UTF-8");
                com.self.web.middlemq.Message message = Message.builder()
                        .key(messageExt.getMsgId())
                        .topic(messageExt.getTopic())
                        .mqType("ROCKETMQ")
                        .body(body)
                        .build();

            } catch (Exception e) {
                log.warn("consume message failed. messageExt:{}", messageExt, e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

        }

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
