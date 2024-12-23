package com.self.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "request-info.cross-request")
@Data
public class RequestInfoProperties {

    private List<String> trustedOrigins;

    private Boolean checkEnable;


}
