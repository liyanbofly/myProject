package com.self.web.middlemq;


import com.self.web.config.RocketProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ConsumerProvider {

    private RocketConsumer rocketConsumer;

    // 可以设置成从配置文件中读取
    private final RocketProperties properties;
    @Resource
    PublisherTaskMessageListener publisherTaskMessageListener;

    @Autowired
    public ConsumerProvider(RocketProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取rocket 消费者
     *
     * @return {@link RocketConsumer}
     */
    public RocketConsumer getRocketConsumer() {
        if (rocketConsumer == null) {
            rocketConsumer = new RocketConsumer(properties);
             RocketMessageHandler rocketMessageHandler=  new RocketMessageHandler();
             rocketMessageHandler.setOpenMessageListener(publisherTaskMessageListener);
             rocketConsumer.setMessageListener(rocketMessageHandler);
            rocketConsumer.doInit();
        }
        return rocketConsumer;
    }








}
