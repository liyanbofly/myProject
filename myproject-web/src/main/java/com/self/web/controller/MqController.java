package com.self.web.controller;


import com.alibaba.fastjson.JSON;
import com.self.model.vo.UserInfo;
import com.self.web.middlemq.RocketProducer;
import com.self.web.middlemq.selfConsumer.RocketConsumerSelf;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MqController {

    @Resource
    RocketProducer rocketProducer;

    // 创建一个生产者，并指定一个组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");


    @Resource
    RocketConsumerSelf rocketConsumerSelf;

    @RequestMapping(value = "/rocketmql/test")
    public String contextLoads() {


        try {

            UserInfo userInfo = new UserInfo();
            userInfo.setName("liyanbo");
            userInfo.setAge(12);
            Message message = new Message("rkt_first_topic", "tag", userInfo.toString().getBytes());

            SendResult sendResult= rocketProducer.producer.send(message);
            producer.send(message, (mqs, msg, arg) -> {
                Long id = (Long) arg;
                long index = id % mqs.size();
                return mqs.get((int) index);
            }, 1L);

        } catch (MQClientException e) {
            System.out.println("e = " + e);
            return "rocketmq test failed";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "rocketmq test";

    }


    String result="";

    @RequestMapping(value = "/rocketmql/asyncSend")
    public String asyncSend() {


        try {
            // 启动生产者

            UserInfo userInfo = new UserInfo();
            userInfo.setName("liyanbo");
            userInfo.setAge(12);
            Message message = new Message("rkt_first_topic", "tag", userInfo.toString().getBytes());



               // 定义发送回调
            SendCallback callback = new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("消息发送成功: %s%n", sendResult);
                    result="消息发送成功: %s%n"+ JSON.toJSONString(sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    System.err.printf("消息发送失败: %s%n", e.getMessage());
                    result="消息发送失败: %s%n"+e.getMessage();
                }
            };

            try {
                // 发送异步消息，selector 设置为 null
                rocketProducer.producer.send(message, callback);
            } catch ( RemotingException e) {
                e.printStackTrace();
            }
            // 等待一段时间以确保回调执行
//            Thread.sleep(5000);
        } catch (MQClientException e) {
            System.out.println("e = " + e);
            return "rocketmq test failed";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "rocketmq async";

    }


    @RequestMapping(value = "/rocketmql/ReSubscribe")
    public String  ReSubscribe(){
        try {
            rocketConsumerSelf.subscribe("rkt_first_topic");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return "rocketmq reSubscribe";
    }

}
