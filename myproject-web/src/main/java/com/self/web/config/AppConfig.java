package com.self.web.config;

import com.self.web.filter.AccessFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {


    @Bean
     public FilterRegistrationBean<AccessFilter>  accessFilterFilterRegistrationBean(){
        FilterRegistrationBean<AccessFilter> registrationBean=new FilterRegistrationBean<>();
        registrationBean.setFilter( new AccessFilter());
        registrationBean.addUrlPatterns("/*"); // 所有请求都经过该过滤器
        return registrationBean;
    }


}
