package com.self.web.middlemq.selfConsumer;

import com.self.web.config.RocketProperties;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RocketConsumerSelf {
    private DefaultMQPushConsumer consumer;

    public  boolean started=false;
    @Resource
    RocketProperties rocketProperties;



    public void getConsumer() {
        if(consumer == null ) {
            RocketProperties.Consumer rocketPropertiesPart = rocketProperties.getConsumer();
            consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup("my-consumer-group");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            // a:实例名称，实例名称不同，消费者的消费进度也不同，实例名称相同，消费者的消费进度相同
            consumer.setInstanceName("generateConsumerInstanceId()-my-consumer-group--实例IP");
            //初次消费起始位置点, 默认:CONSUME_FROM_LAST_OFFSET
            if (rocketPropertiesPart.getConsumeFromWhere() != null) {
                this.consumer.setConsumeFromWhere(rocketPropertiesPart.getConsumeFromWhere());
            }
            // 消费者监听器-处理接收到的消息
            consumer.setMessageListener( new RocketConsumerListenerSelf());
            consumer.setConsumeThreadMin(rocketPropertiesPart.getConsumeThreadMin());
            consumer.setConsumeThreadMax(rocketPropertiesPart.getConsumeThreadMax());
            //并发消费处理队列，如果队列待处理消息首位offset之差超过2000，则放慢pull速度
            consumer.setConsumeConcurrentlyMaxSpan(rocketPropertiesPart.getConsumeConcurrentlyMaxSpan());
            //并发消费处理队列，如果队列待处理消息条数超过1000，则放慢pull速度
            consumer.setPullThresholdForQueue(rocketPropertiesPart.getPullThresholdForQueue());
            //每次pull消息之间的间隔时间，ms
            consumer.setPullInterval(rocketPropertiesPart.getPullInterval());
            // 每次批量拉取的消息数量
            consumer.setPullBatchSize(rocketPropertiesPart.getPullBatchSize());
            // 消费失败重复消费的次数，默认16次 ，失败后会放入 死信队列  topic为xxxx_%DLQ%
            consumer.setMaxReconsumeTimes(rocketPropertiesPart.getMaxReconsumeTimes());
            //这是消费者在拉取不到消息时，暂停当前队列的时间，单位是毫秒。默认值是1000（1秒）。这意味着，如果消费者从当前队列拉取不到消息，它会暂停1秒后再次尝试拉取
            consumer.setSuspendCurrentQueueTimeMillis(rocketPropertiesPart.getSuspendCurrentQueueTimeMillis());
            //pull下来的消息放入内存，异步线程检查这些消息如果超过15min未消费，发回broker
            consumer.setConsumeTimeout(rocketPropertiesPart.getConsumeTimeout());

        }
    }
    /**
     * 订阅topic
     *
     * @param topic 主题
     */
    public void subscribe(String topic) throws MQClientException {
        getConsumer();
        consumer.start();
        consumer.subscribe(topic, "*");

    }

}
