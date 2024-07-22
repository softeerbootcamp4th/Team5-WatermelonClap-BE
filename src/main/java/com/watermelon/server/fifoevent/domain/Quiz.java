package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@RequiredArgsConstructor
public class Quiz {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private OrderEvent orderEvent;

    private String description;
    private String answer;
    private String imgSrc;
    private String title;

    public static Quiz makeQuiz(String question,String answer){
        return Quiz.builder()
                .description(question)
                .answer(answer)
                .build();
    }
    
    @Builder
    public Quiz(String answer, String description) {
        this.answer = answer;
        this.description = description;
    }
    public boolean isCorrect(String answer){
        return answer.equals(this.answer);
    }
}
