package com.watermelon.server;

import com.watermelon.server.orderevent.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.orderevent.service.OrderEventCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockBeans {

    @Bean
    public OrderEventCheckService orderEventCheckService() {
        return (ticket, eventId, quizId, answer) -> ResponseOrderEventResultDto.builder().build();
    }

}
