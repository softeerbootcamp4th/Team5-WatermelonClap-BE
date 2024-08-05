package com.watermelon.server.admin.dto.response;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class ResponseLotteryApplierDto {

    private String uid;
    private String link;
    private int remainChance;
    private int rank;

    public static ResponseLotteryApplierDto from(LotteryApplier lotteryApplier) {

        return ResponseLotteryApplierDto.builder()
                .uid(lotteryApplier.getUid())
                .link(lotteryApplier.getLink().getUri())
                .remainChance(lotteryApplier.getRemainChance())
                .rank(lotteryApplier.getLotteryRank())
                .build();

    }

    public static ResponseLotteryApplierDto createTestDto(){
        return ResponseLotteryApplierDto.builder()
                .uid("uid")
                .link("link")
                .remainChance(1)
                .rank(1)
                .build();
    }

}
