package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Expectation extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private Participant participant;

    private boolean isApproved;

}
