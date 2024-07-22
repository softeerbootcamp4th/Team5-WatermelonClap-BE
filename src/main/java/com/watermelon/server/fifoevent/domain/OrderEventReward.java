package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class OrderEventReward {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private OrderEvent orderEvent;

    private String name;
    private String imgSrc;
}
