package com.self.web.aspect;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.logging.Handler;


/**
 * 日志 切面记录请求日志
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    @Autowired
    private HttpServletRequest request; // 注入HttpServletRequest对象


    @Pointcut("execution(* com.self.web.controller.*.*(..))")
    public void logPointCut() {

    }

    @Before("logPointCut()")
    public void handleBefore(JoinPoint joinPoint) {
        log.info("请求方法：{}，请求参数：{}", joinPoint.getSignature().getName(), JSONUtil.toJsonStr(joinPoint.getArgs()));
    }

    @Around("logPointCut()")
    public Object handleAround(ProceedingJoinPoint joinPoint) {
        Object result=null;
        try {
            System.out.println("环绕增强-执行切点前");
            log.info("环绕增强-执行切点前");
            getRequest(joinPoint);
            result = joinPoint.proceed();
            log.info("环绕增强-执行切点后");
            log.info("响应结果-result:{}", result);
            System.out.println("环绕增强-执行切点后");

        } catch (Throwable t) {
            System.out.println("抛出异常");
            log.info("抛出异常,ex", t);
            System.out.println(t);
        }

        return result;
    }


    public String getRequest(ProceedingJoinPoint joinPoint) {
        String requestMethod = request.getMethod(); // 获取请求方法
        String requestURI = request.getRequestURI(); // 获取请求URI
        String queryString = request.getQueryString(); // 获取请求参数
        Object[] requestArg = joinPoint.getArgs();

        String requestInfo = String.format("Request: %s - %s - %s,requestArg:%s", requestMethod, requestURI, queryString, requestArg);
        log.info("请求参数：{}", requestInfo);
        return requestInfo;


    }

    public String getResponseArg(Object args) {
        if (args == null) {
            return null;
        }
        String resultStr = "";
        if (args instanceof String) {
            resultStr = args.toString();
        } else {
            resultStr = JSONObject.toJSONString(args);
        }
        log.info("Response:{}", resultStr);
        return resultStr;
    }


}
