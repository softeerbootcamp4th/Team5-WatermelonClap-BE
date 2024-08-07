package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.parts.domain.LotteryApplierParts;
import com.watermelon.server.parts.exception.PartsDrawLimitExceededException;
import jakarta.persistence.*;
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

    @Builder.Default
    private boolean isPartsWinner = false;

    @Builder.Default
    private int lotteryRank = -1;

    @Builder.Default
    private boolean isPartsApplier = false;

    @Builder.Default
    private boolean isLotteryApplier = false;

    @Builder.Default
    private int remainChance = 1;

    private String email;

    private String phoneNumber;

    private String name;

    private String address;

    @Builder.Default
    private boolean isLotteryWinnerCheckedByAdmin = false;

    @Builder.Default
    private boolean isPartsWinnerCheckedByAdmin = false;

    @OneToOne(mappedBy = "lotteryApplier", cascade = CascadeType.ALL)
    private Link link;

    @OneToOne(mappedBy = "lotteryApplier")
    private Expectation expectation;

    @OneToMany(mappedBy = "lotteryApplier")
    private List<LotteryApplierParts> lotteryApplierParts;

    public void addNewExpectation(Expectation expectation) {
        this.expectation = expectation;
    }
    public void setLotteryWinnerInfo(String address, String name, String phoneNumber){
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public boolean hasRemainChance(){
        return remainChance > 0;
    }

    public void hasRemainChanceOrThrow(){
        if(!hasRemainChance()) throw new PartsDrawLimitExceededException();
    }

    public void applyLottery(){
        this.isLotteryApplier = true;
    }

    public void lotteryWinnerCheck(){
        this.isLotteryWinnerCheckedByAdmin = true;
    }

    public void partsWinnerCheck(){
        this.isPartsWinner = true;
    }

    public void setLotteryReward(LotteryReward reward) {
        this.lotteryRank = reward.getLotteryRank();
    }

    public void partsLotteryWin(){
        this.isPartsWinner = true;
    }

    public static LotteryApplier createLotteryApplier(String uid){

        LotteryApplier lotteryApplier = LotteryApplier.builder()
                .uid(uid)
                .build();

        lotteryApplier.link = Link.createLink(lotteryApplier);

        return lotteryApplier;
    }

}
