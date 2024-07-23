package com.watermelon.server.orderevent.dto.response;


import com.watermelon.server.orderevent.domain.OrderEvent;
import com.watermelon.server.orderevent.domain.OrderEventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ResponseOrderEventDto {
    private Long eventId;
    private LocalDateTime startDate;
    private OrderEventStatus status;
    private ResponseQuizDto quiz;
    private ResponseRewardDto reward;


    public static ResponseOrderEventDto from(OrderEvent orderEvent) {
        ResponseQuizDto responseQuizDto = null;
        if(orderEvent.getOrderEventStatus().equals(OrderEventStatus.OPEN)
                || orderEvent.getOrderEventStatus().equals(OrderEventStatus.CLOSED)){
            responseQuizDto = ResponseQuizDto.from(orderEvent.getQuiz());
        }
        return ResponseOrderEventDto.builder()
                .eventId(orderEvent.getId())
                .status(orderEvent.getOrderEventStatus())
                .reward(ResponseRewardDto.fromReward(orderEvent.getOrderEventReward()))
                .startDate(orderEvent.getStartDate())
                .quiz(responseQuizDto)
                .build();
    }

}
