package com.self.model.vo;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author weiye.li
 * @date 2023/3/2
 */
@Data
public class OsResponse {
    /**
     * 返回消息
     */
    private String message = "ok";
    /**
     * 商家返回数据 json字符串
     */
    private String data;


    public static OsResponse success(Object data) {
        OsResponse result = new OsResponse();
        JSONObject dataJson = new JSONObject();
        dataJson.put("hasException", false);
        dataJson.put("hasKekaiException", false);
//        dataJson.put("code", MallConstant.OS_SUCCESS_CODE);
//        dataJson.put("msg", MallConstant.OS_SUCCESS_MSG);
        dataJson.put("objectData", data);
        result.setData(JSONUtil.toJsonStr(dataJson));
        return result;
    }

    public static OsResponse error(Exception e) {
        OsResponse result = new OsResponse();
        RemoteResponse exception = new RemoteResponse()
                .setHasException(true)
                .setHasKekaiException(true)
                .setKekaiException(e.getMessage())
                .setException(null);
        result.setData(JSONUtil.toJsonStr(exception));
        return result;
    }



    /**
     * OS需要的异常返回response
     */
    @Data
    @Accessors(chain = true)
    static class RemoteResponse {
        private Boolean HasException;
        /**
         * 异常信息
         */
        private RemoteException Exception;
        /**
         * 是否客开异常
         */
        private Boolean HasKekaiException;
        /**
         * 客开异常描述
         */
        private String KekaiException;

        @Data
        @Accessors(chain = true)
        public static class RemoteException {
            /**
             * 异常码
             */
            private String Code;
            /**
             * 异常信息
             */
            private String Message;

        }


    }


}
