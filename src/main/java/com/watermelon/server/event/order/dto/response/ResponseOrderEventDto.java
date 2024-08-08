package com.watermelon.server.event.order.dto.response;


import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
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
    private LocalDateTime endDate;
    private OrderEventStatus status;
    private ResponseQuizDto quiz;
    private ResponseRewardDto reward;



    public static ResponseOrderEventDto forUser(OrderEvent orderEvent) {
        ResponseQuizDto responseQuizDto = null;
        //열리지 않거나
        if(orderEvent.getOrderEventStatus().equals(OrderEventStatus.OPEN)
                || orderEvent.getOrderEventStatus().equals(OrderEventStatus.CLOSED)){
            responseQuizDto = ResponseQuizDto.forUser(orderEvent.getQuiz());
        }
        ResponseRewardDto responseRewardDto = ResponseRewardDto.fromReward(orderEvent.getOrderEventReward());
        return ResponseOrderEventDto.builder()
                .eventId(orderEvent.getId())
                .status(orderEvent.getOrderEventStatus())
                .reward(responseRewardDto)
                .startDate(orderEvent.getStartDate())
                .endDate(orderEvent.getEndDate())
                .quiz(responseQuizDto)
                .build();
    }
    public static ResponseOrderEventDto forAdmin(OrderEvent orderEvent) {
        ResponseQuizDto responseQuizDto = ResponseQuizDto.forAdmin(orderEvent.getQuiz());
        ResponseRewardDto rewardDto = ResponseRewardDto.fromReward(orderEvent.getOrderEventReward());
        return ResponseOrderEventDto.builder()
                .eventId(orderEvent.getId())
                .status(orderEvent.getOrderEventStatus())
                .reward(rewardDto)
                .startDate(orderEvent.getStartDate())
                .endDate(orderEvent.getEndDate())
                .quiz(responseQuizDto)
                .build();
    }

    @Override
    public String toString() {
        return "ResponseOrderEventDto{" +
                "eventId=" + eventId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", quiz=" + quiz +
                ", reward=" + reward +
                '}';
    }

}
