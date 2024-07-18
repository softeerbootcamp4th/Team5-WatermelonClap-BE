package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Builder
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

}
