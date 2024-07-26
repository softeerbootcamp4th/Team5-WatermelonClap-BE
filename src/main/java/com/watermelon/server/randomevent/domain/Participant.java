package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.parts.domain.ParticipantParts;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String uid;

    private boolean isPartsWinner;

    private int lotteryRank;

    private boolean isPartsApplier;

    private int remainChance;

    private String email;

    private String phoneNumber;

    private String name;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantParts> participantParts;

}
