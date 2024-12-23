package com.slef.serviceTest;


import com.self.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.self.web.WebApplication.class)
@EnableAutoConfiguration
@MapperScan("com.self.mappers")
@ComponentScan("com.self")
public class UserInfoServiceTest {

    @Resource(name = "userInfoServiceImpl")
    UserInfoService userInfoService;


    @Test
    public  void  getUsers(){
//        System.out.println("userInfoService.getUsers() = " + userInfoService.getUsers());

    }

    @Test
    public void sendKafkaMsg(){
        userInfoService.sendKafkaMsg("hello kafka 072901--02");
    }

}
