package com.watermelon.server.event.order.result.domain;


import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import com.watermelon.server.event.order.result.dto.request.OrderResultRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
public class OrderResult {

    @Id@GeneratedValue
    private long id;

    private String applyToken;
    private String phoneNumber;
    private String orderEventId;



    public static OrderResult makeOrderEventApply(String applyToken,
                                                  String orderEventId,
                                                  OrderResultRequestDto orderResultRequestDto){
        return OrderResult.builder()
                .applyToken(applyToken)
                .orderEventId(orderEventId)
                .phoneNumber(orderResultRequestDto.getPhoneNumber())
                .build();
    }

    @Builder
    OrderResult(String applyToken,String orderEventId,String phoneNumber) {
        this.applyToken = applyToken;
        this.orderEventId = orderEventId;
        this.phoneNumber = phoneNumber;
    }
}
