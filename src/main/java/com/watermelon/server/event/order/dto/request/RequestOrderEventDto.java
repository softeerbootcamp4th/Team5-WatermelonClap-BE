package com.watermelon.server.event.order.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderEventDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int maxWinnerCount;

    private RequestQuizDto requestQuizDto;
    private RequestOrderRewardDto requestOrderRewardDto;




}
