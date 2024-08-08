package com.watermelon.server.event.order.dto.response;


import com.watermelon.server.event.order.domain.Quiz;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseQuizDto {
    private Long quizId;
    private String imgSrc;
    private String answer;


    public static ResponseQuizDto forUser(Quiz quiz) {
        return ResponseQuizDto.builder()
                .quizId(quiz.getId())
                .imgSrc(quiz.getImgSrc())
                .answer(null)
                .build();
    }

    public static ResponseQuizDto forAdmin(Quiz quiz) {
        return ResponseQuizDto.builder()
                .quizId(quiz.getId())
                .imgSrc(quiz.getImgSrc())
                .answer(quiz.getAnswer())
                .build();
    }


    @Builder
    public ResponseQuizDto(Long quizId, String imgSrc,String answer ) {
        this.quizId = quizId;
        this.imgSrc = imgSrc;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ResponseQuizDto{" +
                "quizId=" + quizId +
                ", imgSrc='" + imgSrc + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
