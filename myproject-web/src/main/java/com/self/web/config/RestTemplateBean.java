package com.self.web.config;


import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateBean {
    private   RestTemplateBuilder builder=new RestTemplateBuilder();
    @Bean
    public RestTemplate restTemplate() {

        return builder.setConnectTimeout(Duration.ofSeconds(3)).build();
    }

}
