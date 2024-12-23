package com.self.web.middlemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublisherTaskMessageListener  implements OpenMessageListener {
    @Override
    public void onMessage(Message message) {
        log.info("Received message: " + message);
    }
}
