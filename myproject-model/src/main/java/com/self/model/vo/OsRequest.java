package com.self.model.vo;

import lombok.Data;

/**
 * @date 2023/3/2
 */
@Data
public class OsRequest {
    private String sign;
    private String topic;
    private String appKey;
    private String message;
    private String signMethod;
    private String timestamp;


}
