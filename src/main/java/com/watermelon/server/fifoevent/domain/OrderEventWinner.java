package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.*;

@Entity
public class OrderEventWinner {
    @Id @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn
    private OrderEvent orderEvent;

}
