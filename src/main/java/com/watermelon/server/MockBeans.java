package com.watermelon.server;

import com.watermelon.server.orderevent.service.OrderEventCheckService;
import com.watermelon.server.orderevent.service.QuizCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockBeans {

    @Bean
    public OrderEventCheckService orderEventCheckService() {
        return ticket -> false;
    }

    @Bean
    public QuizCheckService quizCheckService() {
        return (quizId, answer) -> false;
    }

}
