package com.watermelon.server;

import com.watermelon.server.event.order.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.event.order.service.OrderEventCheckService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockBeans {

    @Bean
    public OrderEventCheckService orderEventCheckService() {
        return new OrderEventCheckService() {
            @Override
            public ResponseOrderEventResultDto getOrderEventResult(String ticket, Long eventId, Long quizId, String answer) {
                return ResponseOrderEventResultDto.builder().build();
            }
        };
    }

}
