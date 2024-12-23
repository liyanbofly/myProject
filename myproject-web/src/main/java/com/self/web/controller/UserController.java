package com.self.web.controller;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.dmall.open.simple.sdk.producer.service.CallbackService;
import com.dmall.open.simple.sdk.producer.vo.app.CallbackRequest;
import com.self.model.vo.OsRequest;
import com.self.model.vo.OsResponse;
import com.self.model.vo.UserInfo;
import com.self.service.UserInfoService;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

 @Resource
 UserInfoService userInfoService;

 @Resource
 @Lazy
 HikariDataSource hikariDataSource;


 // 因为没有 调用过数据库查询或其它，所以这里的值是null，可以先调用一次查询或插入就可以应用了
 HikariPoolMXBean hikariPoolMXBean;


   public   UserController(HikariDataSource dataSource){
        System.out.println("dataSource = " + dataSource);
        hikariDataSource=dataSource;
       hikariPoolMXBean=hikariDataSource.getHikariPoolMXBean();
    }
// @PostConstruct
// public  void  init(){
//     int l=0;
//     hikariPoolMXBean=hikariDataSource.getHikariPoolMXBean();
// }

    @RequestMapping("/saveUser")
    public OsResponse saveUser(@RequestBody UserInfo userInfo){
        System.out.println("JSONUtil.toJsonStr(userInfo) = " + JSONUtil.toJsonStr(userInfo));
        UserInfo userInfo1=new UserInfo();
        OsResponse osResponse=new OsResponse();
        osResponse.setData("2342");
        return  osResponse;
    }

    @RequestMapping("/saveUser2")
    public UserInfo saveUser2(@RequestBody UserInfo userInfo){
        System.out.println("JSONUtil.toJsonStr(userInfo) = " + JSONUtil.toJsonStr(userInfo));
        UserInfo userInfo1=new UserInfo();
        OsResponse osResponse=new OsResponse();
        osResponse.setData("2342");
        return  userInfo1;
    }

    @RequestMapping("/getUser")
    public UserInfo getUser(){
        hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
        if(hikariPoolMXBean!=null){
            int l= hikariPoolMXBean.getActiveConnections();
        }

        UserInfo userInfo1=new UserInfo();
        userInfo1.setName("张三");
        List<com.self.model.entity.UserInfo> list=userInfoService.getUsers();

        log.error("测试错误日志date:{}",new Date());
//        CallbackRequest callbackRequest=new CallbackRequest();
//        String callBackData="{\"data\":{\"orderType\":1,\"orderId\":\"30000365654\",\"receiverLatitude\":31.242875,\"orderRemark\":\"订单数:1 \",\"goodsWeight\":1.0,\"transportLongitude\":121.38505,\"vender_id\":\"86\",\"receiverPhone\":\"13601900882\",\"goodsActualAmount\":100,\"goodsItemList\":[{\"goodName\":\"田螺肉 1kg/份\",\"goodCount\":1,\"goodSkuId\":1001680571}],\"orderPushTime\":1705645038645,\"shopCode\":\"169781\",\"transportAddress\":\"上海市普陀区真北路1425号\",\"transportLatitude\":31.243034,\"serialNumber\":\"TG805\",\"receiverName\":\"leo\",\"version\":\"1.0\",\"receiverAddress\":\"湖北省省直辖天门市麻洋镇白桥村退役军人服务站湖北省天门市麻洋镇白桥村四组\",\"goodsCount\":1,\"goodsTotalAmount\":100,\"carrierId\":1601,\"receiverLongitude\":121.385161,\"requireReceiveTime\":1705766340000},\"venderId\":86,\"appKey\":\"8b8c864ad79a4083970d714db46e8382\",\"msgKey\":\"carrier_create_order\"}";


        return  userInfo1;

    }


    @RequestMapping("/getUser02")
    public List<com.self.model.entity.UserInfo> getUser02(){
        hikariPoolMXBean = hikariDataSource.getHikariPoolMXBean();
        if(hikariPoolMXBean!=null){
            int l= hikariPoolMXBean.getActiveConnections();
        }

        List<com.self.model.entity.UserInfo> list=userInfoService.getUsers02();
        log.error("测试错误日志date:{}",new Date());
        return  list;
    }



    @RequestMapping("/getUser2")
    public OsResponse getUser2() {
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setName("张三");
        userInfoService.sendKafkaMsg("测试kafka消息");
        OsResponse osResponse=new OsResponse();
        osResponse.setData("2342");
        return  osResponse;
    }

    @RequestMapping("/loginUser")
    public  OsResponse loginUser(UserInfo userInfo, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
         Cookie [] cookies= httpServletRequest.getCookies();
         if(cookies!=null && cookies.length>0) {
             for (int i = 0; i < cookies.length; i++) {
                 if (cookies[i].getName().equals("loginUser")) {
                     System.out.println("cookies[i].getValue() = " + cookies[i].getValue());
                 }
             }
         }




        Cookie cookie=new Cookie("loginUser",userInfo.getName());
        cookie.setDomain("myself.com");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(500);

        httpServletResponse.addCookie(cookie);
        return  new OsResponse();
    }
}

