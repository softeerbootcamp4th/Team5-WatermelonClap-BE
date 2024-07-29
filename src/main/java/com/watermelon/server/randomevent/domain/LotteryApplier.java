package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.parts.domain.LotteryApplierParts;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LotteryApplier extends BaseEntity {

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

    private String address;

    @OneToMany(mappedBy = "lotteryApplier")
    private List<LotteryApplierParts> lotteryApplierParts;

    public void setLotteryWinnerInfo(String address, String name, String phoneNumber){
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

}