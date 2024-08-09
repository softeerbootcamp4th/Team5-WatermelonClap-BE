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

    private String answer;
    private String imgSrc;

    public static Quiz makeQuiz(RequestQuizDto requestQuizDto){
        return Quiz.builder()
                .answer(requestQuizDto.getAnswer())
                .build();
    }

    public static Quiz makeQuizInputId(RequestQuizDto requestQuizDto,Long id){
        return Quiz.builder()
                .id(id)
                .answer(requestQuizDto.getAnswer())
                .build();
    }



    @Builder
    public Quiz(String answer,Long id) {

        this.answer = answer;
        this.id = id;
    }

    public boolean isCorrect(String answer){
        return answer.equals(this.answer);
    }
}
