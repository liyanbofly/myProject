package com.self.service.msg;

import com.self.model.vo.BaseMsg;

public class AbstractMsgConvert<T extends BaseMsg,D> implements MsgConvert<T,D> {

    public  D msgToBizVo(T source,D target){

        D d=null;
        System.out.println("source.getMsgId() = " + source.getMsgId());
        if(source!=null){
            source.setTopic("new_topic");
            d= (D) source;
            biz2OpenMsg(source);
        }
        return  d;

    }


    @Override
    public void biz2OpenMsg(T o)  {
        throw  new RuntimeException("not support operation");
    }
}
