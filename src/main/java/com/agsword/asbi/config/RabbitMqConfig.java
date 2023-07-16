package com.agsword.asbi.config;

import com.agsword.asbi.constant.BiMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author as
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 问答队列
     */
    @Bean
    public Queue aiQuestionQueue() {
        return new Queue(BiMqConstant.AI_QUESTION_QUEUE, true);
    }

    /**
     * 交换机
     */
    @Bean
    DirectExchange qaDirectExchange() {
        return new DirectExchange(BiMqConstant.AI_QUESTION_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(aiQuestionQueue()).to(qaDirectExchange()).with(BiMqConstant.AI_QUESTION_ROUTING_KEY);
    }
}
