package com.watermelon.server.randomevent.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestLotteryWinnerInfoDto {

    private String name;
    private String address;
    private String phoneNumber;

}
