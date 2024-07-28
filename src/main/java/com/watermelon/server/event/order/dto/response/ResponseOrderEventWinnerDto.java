package com.watermelon.server.event.order.dto.response;


import com.watermelon.server.event.order.domain.OrderEventWinner;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseOrderEventWinnerDto {
    private String phoneNumber;
    private LocalDateTime applyDate;
    private String applyAnswer;

    public static ResponseOrderEventWinnerDto forAdmin(OrderEventWinner orderEventWinner){
        return ResponseOrderEventWinnerDto.builder()
                .applyAnswer(orderEventWinner.getApplyAnswer())
                .applyDate(orderEventWinner.getCreatedAt())
                .phoneNumber(orderEventWinner.getPhoneNumber())
                .build();
    }

    @Builder
    public ResponseOrderEventWinnerDto(String phoneNumber, LocalDateTime applyDate, String applyAnswer) {
        this.phoneNumber = phoneNumber;
        this.applyDate = applyDate;
        this.applyAnswer = applyAnswer;
    }
}
