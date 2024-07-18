package com.watermelon.server.fifoevent.dto.response;


import com.watermelon.server.fifoevent.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseQuizDto {
    private String question;


    public static ResponseQuizDto from(Quiz quiz) {
        return new ResponseQuizDto(quiz.getQuestion());
    }
}
