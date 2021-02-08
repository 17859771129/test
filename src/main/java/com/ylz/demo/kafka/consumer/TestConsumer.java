package com.ylz.demo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestConsumer {

    private final static Logger log = LoggerFactory.getLogger(TestConsumer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;
    //一直消费
    /*@KafkaListener(topics = "demo")
    public void consumer(ConsumerRecord consumerRecord){
        Optional kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if(kafkaMessage.isPresent()){
            Object message =kafkaMessage.get();
            log.info(message.toString());
        }
    }*/
}
