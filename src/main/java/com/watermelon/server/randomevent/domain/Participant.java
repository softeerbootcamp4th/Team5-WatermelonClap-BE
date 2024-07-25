package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.parts.domain.ParticipantParts;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Participant extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private boolean isPartsWinner;

    private boolean isLotteryWinner;

    private boolean isPartsApplier;

    private int remainChance;

    private String email;

    private String phoneNumber;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantParts> participantParts;

}
