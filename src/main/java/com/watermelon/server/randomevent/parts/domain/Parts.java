package com.watermelon.server.randomevent.parts.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Parts {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String category;

    @OneToMany(mappedBy = "parts")
    private List<LotteryApplierParts> lotteryApplierParts;

}
