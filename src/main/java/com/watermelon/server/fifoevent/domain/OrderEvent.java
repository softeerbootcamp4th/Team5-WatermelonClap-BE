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

    @OneToOne
    private OrderEventReward orderEventReward;

    @OneToOne(cascade = CascadeType.ALL)
    private Quiz quiz;

    @OneToMany(mappedBy = "fifoEvent")
    private List<OrderEventWinner> orderEventWinner = new ArrayList<>();
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int maxWinnerCount;
    private int winnerCount;

    public boolean isWinnerAddable(){
        if(winnerCount<maxWinnerCount) return true;
        return false;
    }
    public void addWinner(){
        this.winnerCount++;
    }

    public static OrderEvent makeFifoEvent(RequestOrderEventDto requestOrderEventDto){
        Quiz quiz = Quiz.makeQuiz(requestOrderEventDto.getQuestion(), requestOrderEventDto.getAnswer());
        return new OrderEvent(requestOrderEventDto.getMaxWinnerCount(), requestOrderEventDto.getStartTime(), requestOrderEventDto.getEndTime(),quiz);
    }
    public boolean isTimeInEventTime(LocalDateTime time){
        if(time.isAfter(startTime)&&time.isBefore(endTime)){ return true;}
        return false;
    }
    OrderEvent(int maxWinnerCount, LocalDateTime startTime, LocalDateTime endTime, Quiz quiz){
        this.maxWinnerCount = maxWinnerCount;
        this.endTime = endTime;
        this.startTime = startTime;
        this.quiz = quiz;
    }
}
