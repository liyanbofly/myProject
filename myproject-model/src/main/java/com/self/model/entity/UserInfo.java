package com.self.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class UserInfo implements Serializable {
    private Integer id;
    private String userName;
    private String password;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Date birthDate;
    private Byte yn;

}
