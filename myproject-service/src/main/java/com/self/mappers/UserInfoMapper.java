package com.self.mappers;

import com.self.model.entity.UserInfo;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserInfoMapper  {


    /**
     * 获取所有用户信息
     * @return List<UserInfo>
     */
    List<UserInfo> getUsers();

    /**
     * 根据生日获取用户信息
     * @param birthDate
     * @return
     */
    List<UserInfo> getUsers02(String birthDate);

}
