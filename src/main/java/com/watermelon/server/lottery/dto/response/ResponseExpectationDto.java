package com.watermelon.server.lottery.dto.response;


import com.watermelon.server.lottery.domain.Expectation;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ResponseExpectationDto {

    private LocalDateTime uploadDate;
    private String uid;
    private String expectation;
    private Long expectationId;
    private boolean isApproved;


    public static ResponseExpectationDto forAdmin(Expectation expectation) {
        return ResponseExpectationDto.builder()
                .expectation(expectation.getExpectation())
                .uploadDate(expectation.getCreatedAt())
                .isApproved(expectation.isApproved())
                .expectationId(expectation.getId())
                .uid(expectation.getLotteryApplier().getUid())
                .build();
    }
    public static ResponseExpectationDto forUser(Expectation expectation){
        return ResponseExpectationDto.builder()
                .expectation(expectation.getExpectation())
                .build();
    }

    @Builder
    public ResponseExpectationDto(LocalDateTime uploadDate, String uid, String expectation, Long expectationId, boolean isApproved) {
        this.uploadDate = uploadDate;
        this.uid = uid;
        this.expectation = expectation;
        this.expectationId = expectationId;
        this.isApproved = isApproved;
    }
}
