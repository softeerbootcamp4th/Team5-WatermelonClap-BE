package com.watermelon.server.event.order.domain;

import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class OrderEventCheckService {
    private Long eventId;
    private Long quizId;
    private String answer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public boolean isAnswerCorrect(String submitAnswer){
        if(this.answer.equals(submitAnswer)) return true;
        return false;
    }
    public boolean isTimeInEvent(LocalDateTime now){
        if(now.equals(this.startDate) &&now.isBefore(endDate)) return true;
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
