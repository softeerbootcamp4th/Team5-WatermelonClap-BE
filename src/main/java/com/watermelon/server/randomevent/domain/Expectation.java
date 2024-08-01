package com.watermelon.server.randomevent.domain;

import com.watermelon.server.BaseEntity;
import com.watermelon.server.randomevent.dto.request.RequestExpectationDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Expectation extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private LotteryApplier lotteryApplier;
    private String expectation;

    private boolean isApproved;
    public static  Expectation makeExpectation(
            RequestExpectationDto requestExpectationDto
            ,LotteryApplier lotteryApplier){
       Expectation expectation = Expectation.builder()
                .isApproved(false)
                .expectation(requestExpectationDto.getExpectation())
                .lotteryApplier(lotteryApplier)
                .build();
       lotteryApplier.addNewExpectation(expectation);
       return expectation;
    }

    @Builder
    public Expectation(LotteryApplier lotteryApplier, String expectation, boolean isApproved) {
        this.lotteryApplier = lotteryApplier;
        this.expectation = expectation;
        this.isApproved = isApproved;
    }
}
