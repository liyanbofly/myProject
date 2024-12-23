package com.self.service.impl;

import cn.hutool.extra.mail.Mail;
import com.self.mappers.UserInfoMapper;
import com.self.model.entity.UserInfo;
import com.self.service.UserInfoService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.self.model.vo.OsResponse.success;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final String TOPIC = "example_topic";
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Resource
    UserInfoMapper userInfoMapper;
   public List<UserInfo> getUsers(){
         return userInfoMapper.getUsers();
//       return new ArrayList<>();
   }


    public List<UserInfo> getUsers02(){
        LocalDateTime birthdate= LocalDateTime.now().minusDays(-1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = birthdate.format(formatter);

        return userInfoMapper.getUsers02(formattedDate);
//       return new ArrayList<>();
    }






   public Boolean sendKafkaMsg(String msg){
       String uuid=UUID.randomUUID().toString();
       kafkaTemplate.send(new ProducerRecord("test",uuid,msg))
               .addCallback(new ListenableFutureCallback() {
                   @Override
                   public void onSuccess(Object result) {
                       SendResult sendResult = (SendResult) result;
                       System.out.println("sendResult.getRecordMetadata().offset() = " + sendResult.getRecordMetadata().offset());
                   }

                   @Override
                   public void onFailure(Throwable ex) {
                       System.out.println("ex = " + ex);

                   }
               }
       );
       System.out.println("msg = " + msg);
       return true;
   }


    public void sendMessage(String message) {
        kafkaTemplate.send("test", message);
    }



}
