package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Participant extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String phoneNumber;

}
