package com.watermelon.server.randomevent.dto.response;

import com.watermelon.server.randomevent.domain.Participant;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseLotteryWinnerInfoDto {

    String name;
    String address;
    String phoneNumber;

    public static ResponseLotteryWinnerInfoDto from(Participant participant){

        return ResponseLotteryWinnerInfoDto.builder()
                .name(participant.getName())
                .address(participant.getAddress())
                .phoneNumber(participant.getPhoneNumber())
                .build();

    }

}
