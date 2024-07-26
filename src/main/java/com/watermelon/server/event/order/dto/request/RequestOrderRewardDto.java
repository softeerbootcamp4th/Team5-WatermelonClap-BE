package com.watermelon.server.event.order.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestOrderRewardDto {

    private String name;
    private String imgSrc;
}