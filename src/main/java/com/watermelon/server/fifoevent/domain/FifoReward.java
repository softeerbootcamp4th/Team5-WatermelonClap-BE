package com.watermelon.server.fifoevent.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class FifoReward {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private FifoEvent fifoEvent;

    private String title;
    private String rewardImage;
}
