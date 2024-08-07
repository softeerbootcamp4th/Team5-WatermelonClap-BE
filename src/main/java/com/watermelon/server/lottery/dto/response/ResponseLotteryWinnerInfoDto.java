package com.watermelon.server.lottery.dto.response;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseLotteryWinnerInfoDto {

    String name;
    String address;
    String phoneNumber;

    public static ResponseLotteryWinnerInfoDto from(LotteryApplier lotteryApplier){

        return ResponseLotteryWinnerInfoDto.builder()
                .name(lotteryApplier.getName())
                .address(lotteryApplier.getAddress())
                .phoneNumber(lotteryApplier.getPhoneNumber())
                .build();

    }

}
