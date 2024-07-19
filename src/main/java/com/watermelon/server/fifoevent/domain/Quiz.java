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
    private FifoEvent fifoEvent;

    private String question;
    private String answer;

    public static Quiz makeQuiz(String question,String answer){
        return Quiz.builder()
                .question(question)
                .answer(answer)
                .build();
    }

    @Builder
    public Quiz(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }
    public boolean isCorrect(String answer){
        return answer.equals(this.answer);
    }
}
