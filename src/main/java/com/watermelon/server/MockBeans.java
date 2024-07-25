package com.watermelon.server;

import com.watermelon.server.orderevent.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.orderevent.service.OrderEventCheckService;
import com.watermelon.server.randomevent.service.LotteryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MockBeans {

    @Bean
    public OrderEventCheckService orderEventCheckService() {
        return (ticket, eventId, quizId, answer) -> ResponseOrderEventResultDto.builder().build();
    }

    @Bean
    public LotteryService lotteryService() {
        return List::of;
    }

}
