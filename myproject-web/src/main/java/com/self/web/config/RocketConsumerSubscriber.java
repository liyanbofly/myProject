package com.self.web.config;

import com.self.web.middlemq.ConsumerProvider;
import com.self.web.middlemq.selfConsumer.RocketConsumerSelf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * spring boot 启动后执行 TOPIC 消息订阅
 */
@Component
@Slf4j
@Order(value = 1)
public class RocketConsumerSubscriber implements ApplicationRunner {
//    @Resource
//    private ConsumerProvider consumerProvider;

    @Resource
    RocketConsumerSelf rocketConsumerSelf;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        String topic = "TOPIC-a";
//        consumerProvider.getRocketConsumer().subscribe(topic);
        rocketConsumerSelf.getConsumer();
        rocketConsumerSelf.subscribe("rkt_first_topic");
    }
}
