package com.apress.spring.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Processor;

@Component
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Processor.class);

    private StringRedisTemplate template;

    @Autowired
    public Producer(StringRedisTemplate template) {
        this.template = template;
    }

    public void sendTo(String topic, String message) {
        log.info("전송..");
        log.info("topic : "+ topic  + " / message : " + message);
        this.template.convertAndSend(topic, message);
    }
}
