package com.self.web.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http工具类
 *
 * @author weiye.li
 */
public class HttpServletUtil {

    public static String readReqStr(HttpServletRequest request) {
        try {
            return readReqStr(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String readReqStr() {
        return readReqStr(getCurrentHttpServletRequest());
    }

    public static String readReqStr(InputStream body) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(body, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {

            }
        }
        return stringBuilder.toString();
    }


    public static HttpServletRequest getCurrentHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getCurrentHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    public static Map<String, Object> getParamFromReq() {
        Map<String, Object> rs = new HashMap<>(4);
        Enumeration<String> e = getCurrentHttpServletRequest().getParameterNames();
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            Object obj = getCurrentHttpServletRequest().getParameterMap().get(name);
            if (obj != null) {
                String[] values = (String[]) obj;
                if (values.length == 1) {
                    rs.put(name, values[0].trim());
                } else {
                    rs.put(name, values);
                }
            } else {
                rs.put(name, null);
            }
        }
        return rs;
    }

    public static String getRequestValue(String key) {
        HttpServletRequest request = getCurrentHttpServletRequest();
        String jsonParams = HttpServletUtil.readReqStr(request);
        if (!StringUtils.isEmpty(jsonParams)) {
            JSONObject jsonObject = JSON.parseObject(jsonParams);
            if (jsonObject.containsKey(key)) {
                return jsonObject.getString(key);
            }
        }
        if (!StringUtils.isEmpty(request.getParameter(key))) {
            return request.getParameter(key);
        }
        return null;
    }
}
