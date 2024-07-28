package com.watermelon.server.event.order.dto.response;


import com.watermelon.server.event.order.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ResponseQuizDto {
    private Long quizId;
    private String imgSrc;


    public static ResponseQuizDto from(Quiz quiz) {
        return ResponseQuizDto.builder()
                .quizId(quiz.getId())
                .imgSrc(quiz.getImgSrc())
                .build();
    }
}
