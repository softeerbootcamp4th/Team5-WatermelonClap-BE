package com.watermelon.server.event.order.result.domain;


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



    public static OrderResult makeOrderEventApply(String applyToken){
        return OrderResult.builder()
                .applyToken(applyToken)
                .build();
    }

    @Builder
    OrderResult(String applyToken) {
        this.applyToken = applyToken;
    }
}
