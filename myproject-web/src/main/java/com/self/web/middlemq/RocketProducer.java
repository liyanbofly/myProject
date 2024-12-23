package com.self.web.middlemq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public   class RocketProducer  implements InitializingBean {


    public   DefaultMQProducer producer;

    public  void  afterPropertiesSet(){

        try {
            if(producer==null){
                producer= new DefaultMQProducer("group1");
                producer.setNamesrvAddr("127.0.0.1:9876");
                producer.start();
            }
        } catch (MQClientException e) {
            log.error("RocketProducer init error：",e);
            throw new RuntimeException(e);
        }

    }

    public   void shutdownProducer() {
        if (producer != null) {
            producer.shutdown();
        }
    }


}
