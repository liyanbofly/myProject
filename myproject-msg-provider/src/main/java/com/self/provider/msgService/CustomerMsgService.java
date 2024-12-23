package com.self.provider.msgService;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CustomerMsgService {

    @KafkaListener(topics = "test", groupId = "my-consumer-group")
    public void consume(ConsumerRecord<?, ?> record) {
        System.out.println("record.offset() = " + record.offset());
        System.out.println("record.partition() = " + record.partition());
        System.out.println("Received message"+new Date()+": " + record.value());
        if(1==1){
//            throw new RuntimeException("test");
        }

    }


}
