package com.ylz.demo.kafka.producer;

import com.ylz.demo.service.imp.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestProducer {
    private static final Logger log = LoggerFactory.getLogger(TestProducer.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String message){
        log.info("生产消息：[]",message);
        kafkaTemplate.send("demo",message);
    }
}
