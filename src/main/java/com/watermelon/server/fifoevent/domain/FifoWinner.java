package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.*;

@Entity
public class FifoWinner {
    @Id @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn
    private FifoEvent fifoEvent;

}
