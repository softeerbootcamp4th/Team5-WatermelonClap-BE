package com.watermelon.server.orderevent.domain;


import com.watermelon.server.orderevent.dto.request.RequestOrderRewardDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderEventReward {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private OrderEvent orderEvent;

    private String name;
    private String imgSrc;



    public static OrderEventReward makeReward(RequestOrderRewardDto requestOrderRewardDto){
        return OrderEventReward.builder()
                .imgSrc(requestOrderRewardDto.getImgSrc())
                .name(requestOrderRewardDto.getName())
                .build();
    }
    @Builder
    public OrderEventReward(String name,String imgSrc){
        this.name = name;
        this.imgSrc = imgSrc;
    }
}
