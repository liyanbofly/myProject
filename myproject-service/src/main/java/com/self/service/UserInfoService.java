package com.self.service;

import com.self.model.entity.UserInfo;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> getUsers();
    List<UserInfo> getUsers02();
      Boolean sendKafkaMsg(String msg);
}
