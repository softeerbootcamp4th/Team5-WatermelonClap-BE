package com.watermelon.server.lottery.dto.response;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseLotteryRankDto {

    private int rank;

    private boolean isApplied;

    public static ResponseLotteryRankDto from(LotteryApplier lotteryApplier) {
        return ResponseLotteryRankDto.builder()
                .rank(lotteryApplier.getLotteryRank())
                .isApplied(true)
                .build();
    }

    public static ResponseLotteryRankDto createNotApplied(){
        return ResponseLotteryRankDto.builder()
                .rank(-1)
                .isApplied(false)
                .build();
    }

    public static ResponseLotteryRankDto createAppliedTest(){
        return ResponseLotteryRankDto.builder()
                .rank(1)
                .isApplied(true)
                .build();
    }

}
