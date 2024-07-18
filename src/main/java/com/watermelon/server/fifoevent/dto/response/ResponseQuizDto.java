package com.watermelon.server.fifoevent.dto.response;


import com.watermelon.server.fifoevent.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseQuizDto {
    private String problem;


    public static ResponseQuizDto from(Quiz quiz) {
        return new ResponseQuizDto(quiz.getProblem());
    }
}
