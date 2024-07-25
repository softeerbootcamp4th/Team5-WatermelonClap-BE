package com.watermelon.server.randomevent.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseLotteryWinnerDto {

    private String email;
    private int rank;

}
