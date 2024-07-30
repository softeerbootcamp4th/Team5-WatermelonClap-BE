package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class LotteryReward extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private int rank;

    private String imgSrc;

    private int winnerCount;

}
