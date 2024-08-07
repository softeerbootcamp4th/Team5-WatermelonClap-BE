package com.watermelon.server.event.lottery.parts.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class PartsReward extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private int partsRank;

    private String imgSrc;

    private int winnerCount;

}
