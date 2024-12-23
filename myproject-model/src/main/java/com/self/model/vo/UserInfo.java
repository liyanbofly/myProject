package com.self.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserInfo implements Serializable {

    private String name;
    private Integer age;
    /**
     * 会针对不同的日期格式进行转换   需关注 存放数据库时 Date 和 LocalDate 的区别
     * 1. yyyy-MM-dd HH:mm:ss 会转换成 Date
     * 2. yyyy-MM-dd   会转换成 LocalDate
     *
     * ps:
     * 1、 MySQL数据库的datetime类型的字段对应Java的java.util.Date数据类型  mysql-connector-java-5.1.49
     * 2、MySQL数据库的datetime类型的字段对应java.time.LocalDateTime数据类型  mysql-connector-java-8.0.28
     *
     *  3、需验证  java 8 以下实体定义 java.util.Date  直接接收 数据库类型字段是 timestamp
     *            java 8 以上实体定义localDateTime 直接接收 数据库类型字段是 timestamp
     *
     *
     */
    private java.util.Date birthday;
    private LocalDate birthday2;

}

