package com.watermelon.server.randomevent.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseLotteryWinnerInfoDto {

    String name;
    String address;
    String phoneNumber;

}
