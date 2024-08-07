package com.watermelon.server.event.lottery.dto.response;

import com.watermelon.server.event.lottery.domain.LotteryReward;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseRewardInfoDto {

    private String imgSrc;
    private String name;

    public static ResponseRewardInfoDto from(LotteryReward reward) {
        return new ResponseRewardInfoDto(reward.getImgSrc(), reward.getName());
    }

}
