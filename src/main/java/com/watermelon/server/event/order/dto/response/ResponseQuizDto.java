package com.watermelon.server.event.order.dto.response;


import com.watermelon.server.admin.dto.response.ResponseAdminQuizDto;
import com.watermelon.server.event.order.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ResponseQuizDto {
    private Long quizId;
    private String imgSrc;


    public static ResponseQuizDto forUser(Quiz quiz) {
        return ResponseQuizDto.builder()
                .quizId(quiz.getId())
                .imgSrc(quiz.getImgSrc())
                .build();
    }

    public static ResponseAdminQuizDto forAdmin(Quiz quiz) {
        return ResponseAdminQuizDto.makeQuizDtoForAdmin(quiz);
    }


    public ResponseQuizDto(Long quizId, String imgSrc) {
        this.quizId = quizId;
        this.imgSrc = imgSrc;
    }
}
