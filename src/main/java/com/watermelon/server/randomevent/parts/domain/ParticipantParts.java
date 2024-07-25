package com.watermelon.server.randomevent.parts.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.domain.Participant;
import jakarta.persistence.*;

@Entity
public class ParticipantParts extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parts_id")
    private Parts parts;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    private boolean isEquipped;

}