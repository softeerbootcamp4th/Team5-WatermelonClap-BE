package com.watermelon.server.event.order.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Builder
@Getter
public class CurrentEventInfo {
    private Long eventId;
    private Long quizId;
    private String answer;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public boolean isCorrect(String submitAnswer){
        if(this.answer.equals(submitAnswer)) return true;
        return false;
    }
    public boolean isTimeInEvent(LocalDateTime now){
        if(now.equals(this.startDate) &&now.isBefore(endDate)) return true;
        return false;
    }

}
