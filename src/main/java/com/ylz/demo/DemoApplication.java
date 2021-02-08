package com.ylz.demo;

import com.ylz.demo.kafka.consumer.TestConsumer;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = {"classpath:config/application.properties"})
@SpringBootApplication
@ComponentScan({"com.ylz"})
@MapperScan({"com.ylz.demo.mapper"})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
