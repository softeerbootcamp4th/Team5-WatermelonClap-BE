package com.watermelon.server.randomevent.parts.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import jakarta.persistence.*;

@Entity
public class LotteryApplierParts extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parts_id")
    private Parts parts;

    @ManyToOne
    @JoinColumn(name = "lotteryApplier_id")
    private LotteryApplier lotteryApplier;

    private boolean isEquipped;

}