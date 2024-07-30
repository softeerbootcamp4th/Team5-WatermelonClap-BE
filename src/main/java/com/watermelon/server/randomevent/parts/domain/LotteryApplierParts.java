package com.watermelon.server.randomevent.parts.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    /**
     *
     * @param isFirst 파츠가 해당 카테고리에서 처음인 경우
     * @param lotteryApplier 응모자
     * @param parts 파츠
     * @return 응모자 파츠 객체
     */
    public static LotteryApplierParts createApplierParts(boolean isFirst, LotteryApplier lotteryApplier, Parts parts) {
        LotteryApplierParts lotteryApplierParts = new LotteryApplierParts();
        lotteryApplierParts.lotteryApplier = lotteryApplier;
        lotteryApplierParts.parts = parts;
        lotteryApplierParts.isEquipped = isFirst;
        return lotteryApplierParts;
    }

}