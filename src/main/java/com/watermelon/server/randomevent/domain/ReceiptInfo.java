package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

public class ReceiptInfo extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private LotteryApplier lotteryApplier;

    private String address;
    private String name;

}
