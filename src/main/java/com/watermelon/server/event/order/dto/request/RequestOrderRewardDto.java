package com.watermelon.server.event.order.dto.request;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderRewardDto {

    private String name;
    public static RequestOrderRewardDto makeForTest(){
        return RequestOrderRewardDto.builder()
                .name("testName")
                .build();
    }
}
