package com.agsword.asbi.bizmq;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author as
 * CreateTime 2023/7/3 17:48
 */
@SpringBootTest
class BiMassageFailConsumerTest {

    @Resource
    private BiMassageFailConsumer biMassageFailConsumer;


    @Test
    void receiveMessage() {

    }
}