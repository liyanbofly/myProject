package com.self.web;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

//(scanBasePackages = {"com.self"})
@SpringBootApplication(scanBasePackages = {"com.self"})
@MapperScan("com.self.mappers")
@EnableKafka
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
