package com.watermelon.server.event.order.domain;


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
