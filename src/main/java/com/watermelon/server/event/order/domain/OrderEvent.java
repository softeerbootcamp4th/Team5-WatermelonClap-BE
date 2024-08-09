package com.watermelon.server.event.order.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class OrderEvent extends BaseEntity {

    private static final Logger log = LoggerFactory.getLogger(OrderEvent.class);
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
    private int winnerCount;
    private int currentWinnerCount;
    @Setter
    private OrderEventStatus orderEventStatus;

    public OrderEvent(Quiz quiz, LocalDateTime startDate, LocalDateTime endDate) {
        this.quiz = quiz;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    @Builder
    OrderEvent(int winnerCount, LocalDateTime startDate, LocalDateTime endDate, Quiz quiz, OrderEventReward orderEventReward,Long id){
        this.id = id;
        this.winnerCount = winnerCount;
        this.endDate = endDate;
        this.startDate = startDate;
        this.quiz = quiz;
        this.orderEventReward = orderEventReward;
        this.orderEventStatus = OrderEventStatus.UPCOMING;
    }



    public static OrderEvent makeOrderEventWithOutImage(RequestOrderEventDto requestOrderEventDto){
        Quiz quiz = Quiz.makeQuiz(requestOrderEventDto.getRequestQuizDto());
        OrderEventReward reward = OrderEventReward.makeReward(requestOrderEventDto.getRequestOrderRewardDto());
        return OrderEvent.builder()
                .quiz(quiz)
                .startDate(requestOrderEventDto.getStartDate())
                .endDate(requestOrderEventDto.getEndDate())
                .orderEventReward(reward)
                .winnerCount(requestOrderEventDto.getWinnerCount())
                .build();
    }
    public static OrderEvent makeOrderEventWithImage(RequestOrderEventDto requestOrderEventDto,String rewardImage){
        Quiz quiz = Quiz.makeQuiz(requestOrderEventDto.getRequestQuizDto());
        OrderEventReward reward = OrderEventReward.makeRewardWithImage(requestOrderEventDto.getRequestOrderRewardDto(),rewardImage);
        return OrderEvent.builder()
                .quiz(quiz)
                .startDate(requestOrderEventDto.getStartDate())
                .endDate(requestOrderEventDto.getEndDate())
                .orderEventReward(reward)
                .winnerCount(requestOrderEventDto.getWinnerCount())
                .build();
    }

    public static OrderEvent makeOrderEventWithInputIdForDocumentation(RequestOrderEventDto requestOrderEventDto, Long id){
        Quiz quiz = Quiz.makeQuizInputId(requestOrderEventDto.getRequestQuizDto(),id);
        OrderEventReward reward = OrderEventReward.makeRewardInputId(requestOrderEventDto.getRequestOrderRewardDto(),id);
        return OrderEvent.builder()
                .id(id)
                .quiz(quiz)
                .startDate(requestOrderEventDto.getStartDate())
                .endDate(requestOrderEventDto.getEndDate())
                .orderEventReward(reward)
                .winnerCount(requestOrderEventDto.getWinnerCount())
                .build();
    }





    public void changeOrderEventStatusByTime(LocalDateTime now){
        if(orderEventStatus.equals(OrderEventStatus.END)||orderEventStatus.equals(OrderEventStatus.CLOSED)) return;
        if(orderEventStatus.equals(OrderEventStatus.UPCOMING)){
            if(now.isAfter(startDate)) {
                this.orderEventStatus = OrderEventStatus.OPEN;
                log.info("EVENT OPEN");
            }
        }
        if(orderEventStatus.equals(OrderEventStatus.OPEN)){
            if(now.isAfter(endDate)){
                this.orderEventStatus = OrderEventStatus.END;
                log.info("EVENT END");
            }
        }
    }

    public boolean isTimeInEventTime(LocalDateTime time){
        if(time.isAfter(startDate)&&time.isBefore(endDate)){ return true;}
        return false;
    }
    public void openEvent(){
        this.orderEventStatus = OrderEventStatus.OPEN;
    }
    public boolean isWinnerAddable(){
        if(currentWinnerCount < winnerCount) return true;
        return false;
    }
    public void addWinner(){
        this.currentWinnerCount++;
    }
}
