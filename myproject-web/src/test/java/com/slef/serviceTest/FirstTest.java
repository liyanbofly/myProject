package com.slef.serviceTest;
import com.alibaba.fastjson.JSON;
import com.self.model.vo.UserInfo;
import com.self.web.util.RedisTemplate;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


/**
 *  加载注解，使test类可以获取spring容器中的bean
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class FirstTest {

    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(7);
        System.out.println(localDateTime);
        System.out.println("hello world");

        Set<String> set02 = new HashSet<>();
        set02.add("key01");
        set02.add("key02");
        System.out.println("JSON.toJSONString(set02) = " + JSON.toJSONString(set02));


        LocalDateTime localDateTime1 = LocalDateTime.parse("2024-01-23 19:56:27", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        localDateTime1 = localDateTime1.minusDays(7);
        System.out.println("localDateTime1 = " + localDateTime1);
    }


    @Test
    public void test02() {
      Class<?> userClass =UserInfo.class;

        // 获取UserInfo类的所有声明字段
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("field.getName() = " + field.getName());
        }

    }


    @Test
    public void getBoolean() {
        Long l1 = 12L;
        String s1 = "12";
        System.out.println("s1.equals(l1) = " + s1.equals(l1.toString()));


    }

    // 生成一个冒泡方法
    public int get(){
       return 1;
    }




}
