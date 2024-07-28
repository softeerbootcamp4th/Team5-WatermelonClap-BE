package com.watermelon.server.admin.dto.response;

import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.dto.response.ResponseQuizDto;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;


@Getter
@SuperBuilder
public class ResponseAdminQuizDto extends ResponseQuizDto {
    private String answer;

    public static ResponseAdminQuizDto makeQuizDtoForAdmin(Quiz quiz) {
        return ResponseAdminQuizDto.builder()
                .quiz(quiz)
                .build();
    }
    @Builder
    ResponseAdminQuizDto(Quiz quiz) {
        super(quiz.getId(), quiz.getImgSrc());
        this.answer = quiz.getAnswer();
    }
}
