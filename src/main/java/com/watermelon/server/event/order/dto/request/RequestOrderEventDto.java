package com.watermelon.server.event.order.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderEventDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int winnerCount;

    private RequestQuizDto requestQuizDto;
    private RequestOrderRewardDto requestOrderRewardDto;

    public static RequestOrderEventDto makeForTest(RequestQuizDto requestQuizDto,RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startDate(LocalDateTime.now().plusHours(10))
                .endDate(LocalDateTime.now().plusHours(20))
                .winnerCount(100)
                .build();
    }
}
