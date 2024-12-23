package com.self.web.filter;

import javafx.scene.paint.Stop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 增加 mdc traceId
 * 增加 请求响应日志 和请求响应时间
 */
@Slf4j
@Component
@WebFilter(value = "MyFilter",urlPatterns = "/*")
public class AccessFilter extends OncePerRequestFilter {

    String TRACE_ID = "tid";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceIdValue = "";
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 记录请求信息
        log.info("Request:开始时间， {} -{},body:{}", request.getMethod(), request.getRequestURI());

        if (StringUtils.isNotBlank(request.getHeader(TRACE_ID))) {
            traceIdValue = request.getHeader(TRACE_ID);
        } else {
            traceIdValue = UUID.randomUUID().toString();
        }
        MDC.put(TRACE_ID, traceIdValue);


        stopWatch.stop();
        log.info("响应内容:耗时：{}， {} -{},body:{}", stopWatch.getTotalTimeNanos(), response.getStatus(), response.getContentType());
        filterChain.doFilter(request, response);


    }

    @Override
    public void destroy() {
        MDC.clear();
    }
}
