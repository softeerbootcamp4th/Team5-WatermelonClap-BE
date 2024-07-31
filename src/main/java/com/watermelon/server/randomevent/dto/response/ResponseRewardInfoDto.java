package com.watermelon.server.randomevent.dto.response;

import com.watermelon.server.randomevent.domain.LotteryReward;
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
