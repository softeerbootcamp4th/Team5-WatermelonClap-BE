package com.watermelon.server.randomevent.dto.response;

import com.watermelon.server.randomevent.domain.LotteryApplier;
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
