package com.self.web.middlemq;

import com.alibaba.fastjson.JSON;
import com.self.web.config.RocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

@Slf4j
public class RocketConsumer   {

    private DefaultMQPushConsumer consumer;

    private MessageListener messageListener;
    RocketProperties rocketProperties=new RocketProperties();
    private static final String tags = "*";
    private boolean started = false;

    public RocketConsumer(RocketProperties rocketProperties ) {
        this.rocketProperties=rocketProperties;
    }

    public  void  doInit(){
        if (started) {
            return;
        }
        try {
            createConsumer();
            start();
            started = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    public void  createConsumer(){
        RocketProperties.Consumer consumerProperties = rocketProperties.getConsumer();
        //"my-consumer-group"
            consumer=new DefaultMQPushConsumer();

        consumer.setConsumerGroup("my-consumer-group");
        consumer.setNamesrvAddr("http://127.0.0.1:9876");
        // q:InstanceName的作用
        // a:实例名称，实例名称不同，消费者的消费进度也不同，实例名称相同，消费者的消费进度相同
        consumer.setInstanceName("generateConsumerInstanceId()-my-consumer-group--实例IP");
        //初次消费起始位置点, 默认:CONSUME_FROM_LAST_OFFSET
        if (consumerProperties.getConsumeFromWhere() != null) {
            this.consumer.setConsumeFromWhere(consumerProperties.getConsumeFromWhere());
        }
        // 消费者监听器-处理接收到的消息
       consumer.setMessageListener(this.messageListener);
        consumer.setConsumeThreadMin(consumerProperties.getConsumeThreadMin());
        consumer.setConsumeThreadMax(consumerProperties.getConsumeThreadMax());
        //并发消费处理队列，如果队列待处理消息首位offset之差超过2000，则放慢pull速度
        consumer.setConsumeConcurrentlyMaxSpan(consumerProperties.getConsumeConcurrentlyMaxSpan());
        //并发消费处理队列，如果队列待处理消息条数超过1000，则放慢pull速度
        consumer.setPullThresholdForQueue(consumerProperties.getPullThresholdForQueue());
        //每次pull消息之间的间隔时间，ms
        consumer.setPullInterval(consumerProperties.getPullInterval());
        // 每次批量拉取的消息数量
        consumer.setPullBatchSize(consumerProperties.getPullBatchSize());
        // 消费失败重复消费的次数，默认16次 ，失败后会放入 死信队列  topic为xxxx_%DLQ%
        consumer.setMaxReconsumeTimes(consumerProperties.getMaxReconsumeTimes());
        //这是消费者在拉取不到消息时，暂停当前队列的时间，单位是毫秒。默认值是1000（1秒）。这意味着，如果消费者从当前队列拉取不到消息，它会暂停1秒后再次尝试拉取
        consumer.setSuspendCurrentQueueTimeMillis(consumerProperties.getSuspendCurrentQueueTimeMillis());
        //pull下来的消息放入内存，异步线程检查这些消息如果超过15min未消费，发回broker
        consumer.setConsumeTimeout(consumerProperties.getConsumeTimeout());
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }


    public DefaultMQPushConsumer getConsumer() {
        if (consumer == null) {
            doInit();
        }
        return consumer;
    }

    /**
     * 订阅topic
     *
     * @param topic 主题
     */
    public void subscribe(String topic) throws MQClientException {
        log.info("rocketmq订阅topic: {}", topic);
        getConsumer().subscribe(topic, tags);
        DefaultMQProducer defaultMQProducer=new DefaultMQProducer();
    }

    /**
     * 取消订阅
     *
     * @param topic 主题
     */
    public void unsubscribe(String topic) {
        getConsumer().unsubscribe(topic);
    }


    public void start() throws Exception {
        try {
            log.info("{} start consumer...", consumer.getConsumerGroup());
            consumer.start();
        } catch (MQClientException e) {
            log.error("start RocketMQ consumer [topic:{},group:{}] error:", "topic-xxx",  consumer.getConsumerGroup(), e);
            throw new Exception(e);
        }

    }
}
