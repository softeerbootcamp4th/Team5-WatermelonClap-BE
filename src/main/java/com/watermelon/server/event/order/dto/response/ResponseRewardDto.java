package com.watermelon.server.event.order.dto.response;

import com.watermelon.server.event.order.domain.OrderEventReward;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResponseRewardDto {
    private String name;
    private String imgSrc;

    public static ResponseRewardDto fromReward(OrderEventReward orderEventReward){
        return ResponseRewardDto.builder()
                .name(orderEventReward.getName())
                .imgSrc(orderEventReward.getImgSrc())
                .build();
    }
}
