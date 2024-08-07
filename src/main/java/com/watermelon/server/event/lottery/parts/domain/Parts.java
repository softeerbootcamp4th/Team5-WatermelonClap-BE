package com.watermelon.server.event.lottery.parts.domain;

import jakarta.persistence.*;
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
public class Parts {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PartsCategory category;

    private String imgSrc;

    @OneToMany(mappedBy = "parts")
    private List<LotteryApplierParts> lotteryApplierParts;

}
