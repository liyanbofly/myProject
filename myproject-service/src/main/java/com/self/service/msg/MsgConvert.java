package com.self.service.msg;

public interface MsgConvert<T,D> {

    /**
     * 第一步消息转业务实体
     * @param o
     */
    public void  biz2OpenMsg(T o) throws Exception;

}
