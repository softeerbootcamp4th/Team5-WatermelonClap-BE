package com.watermelon.server.event.order.domain;


import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class OrderEventWinner {
    @Id @GeneratedValue
    private Long id;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn
    private OrderEvent orderEvent;

    public static OrderEventWinner makeWinner(OrderEvent orderEvent
            , OrderEventWinnerRequestDto orderEventWinnerRequestDto) {
        return OrderEventWinner.builder()
                .phoneNumber(orderEventWinnerRequestDto.getPhoneNumber())
                .orderEvent(orderEvent)
                .build();
    }
    @Builder
    public OrderEventWinner(String phoneNumber,OrderEvent orderEvent) {
        this.phoneNumber = phoneNumber;
        this.orderEvent = orderEvent;
    }

}
