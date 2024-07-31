package com.watermelon.server.event.order.service;

import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@NoArgsConstructor
public class OrderEventCheckService {
    private Long eventId;
    private Long quizId;
    private String answer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public OrderEventCheckService(Long eventId, Long quizId, String answer, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventId = eventId;
        this.quizId = quizId;
        this.answer = answer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void refreshInforMation(OrderEvent orderEvent){
        this.eventId = orderEvent.getId();
        this.quizId =orderEvent.getQuiz().getId();
        this.answer = orderEvent.getQuiz().getAnswer();
        this.startDate = orderEvent.getStartDate();
        this.endDate = orderEvent.getEndDate();
    }
    public boolean isAnswerCorrect(String submitAnswer){
        if(this.answer.equals(submitAnswer)) return true;
        return false;
    }
    public boolean isTimeInEvent(LocalDateTime now){
        if(now.isAfter(this.startDate) &&now.isBefore(endDate)) return true;
        return false;
    }
    public boolean isEventAndQuizIdWrong( Long eventId,Long quizId) {
        if(this.eventId.equals(eventId) && this.quizId.equals(quizId)) return true;
        return false;
    }
    public void checkingInfoErrors( Long eventId, Long quizId)
            throws WrongOrderEventFormatException, NotDuringEventPeriodException {
        if (!isEventAndQuizIdWrong(eventId, quizId)) throw new WrongOrderEventFormatException();
        if (!isTimeInEvent(LocalDateTime.now())) throw new NotDuringEventPeriodException();
    }
}
