package com.watermelon.server.event.order.dto.request;


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
}
