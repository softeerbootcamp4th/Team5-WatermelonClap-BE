package com.watermelon.server.event.lottery.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestLotteryWinnerInfoDto {

    private String name;
    private String address;
    private String phoneNumber;

}
