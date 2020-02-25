package com.apress.spring.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WebsocketProducer {
    private static Logger log = LoggerFactory.getLogger(WebsocketProducer.class);

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public WebsocketProducer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendMessageTo(String topic, String message) {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ")
                .append(dateFormatter.format(new Date()))
                .append("] ")
                .append(message);
        log.info(topic + " / " + builder.toString());
        simpMessagingTemplate.convertAndSend("/topic/" + topic, builder.toString());
    }

}
