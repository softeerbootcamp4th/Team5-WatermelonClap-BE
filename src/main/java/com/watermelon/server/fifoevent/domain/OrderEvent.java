package com.watermelon.server.fifoevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class OrderEvent extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderEventReward orderEventReward;

    @OneToOne(cascade = CascadeType.ALL)
    private Quiz quiz;

    @OneToMany(mappedBy = "orderEvent")
    private List<OrderEventWinner> orderEventWinner = new ArrayList<>();
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int maxWinnerCount;
    private int winnerCount;

    public boolean isWinnerAddable(){
        if(winnerCount<maxWinnerCount) return true;
        return false;
    }
    public void addWinner(){
        this.winnerCount++;
    }

    public static OrderEvent makeOrderEvent(RequestOrderEventDto requestOrderEventDto){
        Quiz quiz = Quiz.makeQuiz(requestOrderEventDto.getRequestQuizDto());
        OrderEventReward reward = OrderEventReward.makeReward(requestOrderEventDto.getRequestOrderRewardDto());
        return new OrderEvent(requestOrderEventDto.getMaxWinnerCount(), requestOrderEventDto.getStartTime(), requestOrderEventDto.getEndTime(),quiz,reward);
    }
    public boolean isTimeInEventTime(LocalDateTime time){
        if(time.isAfter(startDate)&&time.isBefore(endDate)){ return true;}
        return false;
    }
    OrderEvent(int maxWinnerCount, LocalDateTime startDate, LocalDateTime endDate, Quiz quiz,OrderEventReward orderEventReward){
        this.maxWinnerCount = maxWinnerCount;
        this.endDate = endDate;
        this.startDate = startDate;
        this.quiz = quiz;
        this.orderEventReward = orderEventReward;
    }
}
