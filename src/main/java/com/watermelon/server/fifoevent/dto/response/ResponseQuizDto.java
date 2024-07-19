package com.watermelon.server.fifoevent.dto.response;


import com.watermelon.server.fifoevent.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseQuizDto {
    private String question;
    private Long fifoEventId;


    public static ResponseQuizDto from(Quiz quiz,Long fifoEventId) {
        return new ResponseQuizDto(quiz.getQuestion(),fifoEventId);
    }
}
