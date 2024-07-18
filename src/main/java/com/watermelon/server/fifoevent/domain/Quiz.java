package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
public class Quiz {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private FifoEvent fifoEvent;

    private String problem;
    private String answer;

}
