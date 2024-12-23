package com.self.web.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.self.model.vo.OsRequest;
import com.self.model.vo.OsResponse;
import com.self.web.util.HttpServletUtil;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class MallMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
    /**
     * 需要转换请求的路径
     */
    private String apiMallPath;


    public MallMappingJackson2HttpMessageConverter(String apiMallPath) {
        this.apiMallPath = apiMallPath;
    }

    /**
     * 判断是否需要转换
     *  接收参数：通过前一下方未能 canRead 后，会调用 read 方法，这里可以通过重写 read 方法来实现自定义的转换逻辑。
     *  响应参数： canWrite 返回true  才会调用 write 方法，这里可以通过重写 write 方法来实现自定义的转换逻辑。
     *
     *  *******将请求对象转换，取部分内容作为接口参数传入
     *
     *
     * @return
     */
    @Override
    public Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {

        System.out.println("type = " + type);
        System.out.println("contextClass = " + contextClass);
        if(apiMallPath.indexOf("user")==-1){
            System.out.println("user");
            return super.read(type, contextClass, inputMessage);
        }
        OsRequest osRequest = JSONUtil.toBean(StrUtil.str(IoUtil.readBytes(inputMessage.getBody()), StandardCharsets.UTF_8), OsRequest.class);

        if (osRequest == null || osRequest.getSign() == null) {
            throw new HttpMessageNotReadableException("无效请求参数，非开放平台下发json结构");
        }

//        return this.objectMapper.readValue(osRequest.getMessage(), getJavaType(type, contextClass));
        return null;
    }

    /**
     * 对输出信息进行转换，转换成统一对外输出对象
     * @param object
     * @param type
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //进入全局异常或者url不匹配, 则不转换
        if (object instanceof OsResponse || !this.isMatch()) {
            super.writeInternal(object, type, outputMessage);
            return;
        }
        super.writeInternal(OsResponse.success(object), OsResponse.class, outputMessage);
    }


    private boolean isMatch() {
        HttpServletRequest request = HttpServletUtil.getCurrentHttpServletRequest();
        String uri = request.getRequestURI();
        return PatternMatchUtils.simpleMatch((StrUtil.isBlank(apiMallPath) ? "" : apiMallPath).split(StrUtil.COMMA), uri);
    }





}
