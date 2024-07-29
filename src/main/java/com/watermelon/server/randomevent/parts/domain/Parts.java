package com.watermelon.server.randomevent.parts.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Parts {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String category;

    private String imgSrc;

    @OneToMany(mappedBy = "parts")
    private List<LotteryApplierParts> lotteryApplierParts;

}
