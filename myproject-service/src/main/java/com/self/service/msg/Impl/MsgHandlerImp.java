package com.self.service.msg.Impl;

import com.self.model.vo.BaseMsg;
import com.self.service.msg.AbstractMsgConvert;
import org.springframework.stereotype.Component;

@Component
public class MsgHandlerImp extends AbstractMsgConvert<BaseMsg,BaseMsg> {

    @Override
    public void biz2OpenMsg(BaseMsg o)  {
        System.out.println("biz2OpenMsg-Img");
    }

}
