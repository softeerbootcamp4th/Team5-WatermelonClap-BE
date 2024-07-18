package com.watermelon.server.fifoevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class FifoEvent extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private FifoReward fifoReward;

    @OneToOne
    private Quiz quiz;

    @OneToMany(mappedBy = "fifoEvent")
    private List<FifoWinner> fifoWinner = new ArrayList<>();

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String title;
    private String imageUrl;
    private int winnerCount;
}
