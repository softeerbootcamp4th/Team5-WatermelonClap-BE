package com.watermelon.server.event.order.dto.request;


import com.watermelon.server.event.order.domain.OrderEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventWinnerRequestDto {
    private String phoneNumber;

    public static OrderEventWinnerRequestDto forTest() {
        return OrderEventWinnerRequestDto.builder()
                .phoneNumber("testPhoneNumber")
                .build();
    }
}
