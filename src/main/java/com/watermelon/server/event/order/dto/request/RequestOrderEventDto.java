package com.watermelon.server.event.order.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@NoArgsConstructor
public class RequestOrderEventDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int winnerCount;

    private RequestQuizDto quiz;
    private RequestOrderRewardDto reward;
    @Builder
    public RequestOrderEventDto(LocalDateTime startDate, LocalDateTime endDate, int winnerCount, RequestQuizDto quiz, RequestOrderRewardDto reward) {
        this.startDate = startDate.truncatedTo(ChronoUnit.SECONDS);
        this.endDate = endDate.truncatedTo(ChronoUnit.SECONDS);
        this.winnerCount = winnerCount;
        this.quiz = quiz;
        this.reward = reward;
    }



    public static RequestOrderEventDto makeForTestOpen10HoursLater(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .reward(requestOrderRewardDto)
                .quiz(requestQuizDto)
                .startDate(LocalDateTime.now().plusHours(10))
                .endDate(LocalDateTime.now().plusHours(20))
                .winnerCount(100)
                .build();
    }
    public static RequestOrderEventDto makeForTestOpened(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .reward(requestOrderRewardDto)
                .quiz(requestQuizDto)
                .startDate(LocalDateTime.now().minusHours(10))
                .endDate(LocalDateTime.now().plusHours(20))
                .winnerCount(100)
                .build();
    }
    public static RequestOrderEventDto makeForTestOpenAfter1SecondCloseAfter3Second(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .reward(requestOrderRewardDto)
                .quiz(requestQuizDto)
                .startDate(LocalDateTime.now().plusSeconds(1))
                .endDate(LocalDateTime.now().plusSeconds(3))
                .winnerCount(100)
                .build();
    }
    @Override
    public String toString() {
        return "RequestOrderEventDto{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", winnerCount=" + winnerCount +
                ", quiz=" + quiz +
                ", reward=" + reward +
                '}';
    }
}
