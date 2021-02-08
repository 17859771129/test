package com.ylz.demo;

import com.ylz.demo.annotation.TestAnnotation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

//@ComponentScan("com.ylz")
@SpringBootTest
class DemoApplicationTests {

    @Test
    public void test() {
        System.out.println("dfd");
    }



}
