package com.watermelon.server.fifoevent.dto.response;


import com.watermelon.server.fifoevent.domain.OrderEvent;
import com.watermelon.server.fifoevent.domain.OrderEventStatus;
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


    public static ResponseOrderEventDto from(OrderEvent orderEvent,OrderEventStatus orderEventStatus) {
        ResponseQuizDto responseQuizDto = null;
        if(orderEventStatus.equals(OrderEventStatus.OPEN)|| orderEventStatus.equals(OrderEventStatus.CLOSED)){
            responseQuizDto = ResponseQuizDto.from(orderEvent.getQuiz());
        }
        return ResponseOrderEventDto.builder()
                .eventId(orderEvent.getId())
                .status(orderEventStatus)
                .reward(ResponseRewardDto.fromReward(orderEvent.getOrderEventReward()))
                .startDate(orderEvent.getStartDate())
                .quiz(responseQuizDto)
                .build();
    }

}
