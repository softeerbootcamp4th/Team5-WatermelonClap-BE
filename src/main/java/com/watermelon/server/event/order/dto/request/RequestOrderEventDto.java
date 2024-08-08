package com.watermelon.server.event.order.dto.request;

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

    private RequestQuizDto requestQuizDto;
    private RequestOrderRewardDto requestOrderRewardDto;
    @Builder
    public RequestOrderEventDto(LocalDateTime startDate, LocalDateTime endDate, int winnerCount, RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto) {
        this.startDate = startDate.truncatedTo(ChronoUnit.SECONDS);
        this.endDate = endDate.truncatedTo(ChronoUnit.SECONDS);
        this.winnerCount = winnerCount;
        this.requestQuizDto = requestQuizDto;
        this.requestOrderRewardDto = requestOrderRewardDto;
    }




    public static RequestOrderEventDto makeForTestOpen10HoursLater(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startDate(LocalDateTime.now().plusHours(10))
                .endDate(LocalDateTime.now().plusHours(20))
                .winnerCount(100)
                .build();
    }
    public static RequestOrderEventDto makeForTestOpened(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startDate(LocalDateTime.now().minusHours(10))
                .endDate(LocalDateTime.now().plusHours(20))
                .winnerCount(100)
                .build();
    }
    public static RequestOrderEventDto makeForTestOpenAfter1SecondCloseAfter3Second(RequestQuizDto requestQuizDto, RequestOrderRewardDto requestOrderRewardDto){
        return RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startDate(LocalDateTime.now().plusSeconds(1))
                .endDate(LocalDateTime.now().plusSeconds(3))
                .winnerCount(100)
                .build();
    }
}
