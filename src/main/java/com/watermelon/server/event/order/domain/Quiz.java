package com.watermelon.server.event.order.domain;


import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
@NoArgsConstructor
public class Quiz {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private OrderEvent orderEvent;

    private String description;
    private String answer;
    private String imgSrc;
    private String title;

    public static Quiz makeQuiz(RequestQuizDto requestQuizDto){
        return Quiz.builder()
                .description(requestQuizDto.getDescription())
                .answer(requestQuizDto.getAnswer())
                .imgSrc(requestQuizDto.getImgSrc())
                .title(requestQuizDto.getTitle())
                .build();
    }
    @Builder
    public Quiz(String answer, String description,String imgSrc,String title) {
        this.answer = answer;
        this.description = description;
        this.imgSrc = imgSrc;
        this.title = title;
    }
    public boolean isCorrect(String answer){
        return answer.equals(this.answer);
    }
}
