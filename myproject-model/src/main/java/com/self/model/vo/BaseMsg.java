package com.self.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseMsg implements Serializable {

    private  String msgId;
    private  String topic;

}
