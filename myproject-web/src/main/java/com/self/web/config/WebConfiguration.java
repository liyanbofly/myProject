package com.self.web.config;

import com.self.web.interceptor.CorsControlInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

// extends WebMvcConfigurerAdapter 对比WebMvcConfigurer 区别


@Configuration
public class WebConfiguration  implements WebMvcConfigurer {

    @Resource
    private CorsControlInterceptor corsControlInterceptor;

    @Resource
    RequestInfoProperties requestInfoProperties;




//    @Bean
//    public MallMappingJackson2HttpMessageConverter mallMessageConverter() {
//        return new MallMappingJackson2HttpMessageConverter("/user");
//    }


    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(corsControlInterceptor).addPathPatterns("/**");
    }


}
