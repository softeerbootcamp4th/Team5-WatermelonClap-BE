package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class LotteryReward extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private int lotteryRank;

    private String imgSrc;

    private String name;

    private int winnerCount;

}
