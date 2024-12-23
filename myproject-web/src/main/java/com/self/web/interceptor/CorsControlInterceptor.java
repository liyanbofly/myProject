package com.self.web.interceptor;

import com.self.web.config.RequestInfoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



/**
 * @Description: TODO
 * @Author
 * @Date
 *
 *
 * 如果cookie 不能正常携带
 *
 *
 **/
@Component
@Slf4j
public class CorsControlInterceptor  implements HandlerInterceptor {
    @Resource
    RequestInfoProperties requestInfoProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 预请求直接放行
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            //   如果还有问题可以尝试添加下面的代码
            //  response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            return true;
        }

        String origin = request.getHeader("Origin");
        if(requestInfoProperties.getCheckEnable()!=null &&  requestInfoProperties.getCheckEnable()) {
            for (String allowedOrigin : requestInfoProperties.getTrustedOrigins()) {
                if (allowedOrigin.indexOf(origin) > -1) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
                    //允许携带凭证
                    response.setHeader("Access-Control-Allow-Credentials","true");
                    // 设置预检请求的有效期（单位：秒）
                    response.setHeader("Access-Control-Max-Age", "3600");
                    return true;
                }
            }
        }else{
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            //允许携带凭证
            response.setHeader("Access-Control-Allow-Credentials","true");
            // 设置预检请求的有效期（单位：秒）
            response.setHeader("Access-Control-Max-Age", "3600");
            return  true;
        }
        log.info("CSRF拦截:{}", origin);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return  false;
    }
}
