package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.result.domain.OrderResult;
import lombok.Getter;
import org.redisson.api.RSet;
import org.springframework.stereotype.Service;

@Service
public class OrderResultQueryService {
    private final RSet<OrderResult> orderResultRset;

    @Getter
    private final int maxCount;

    public OrderResultQueryService(RSet orderResultRset) {
        this.orderResultRset = orderResultRset;
        this.maxCount= 100;
    }

    public boolean isOrderApplyNotFull(){
        int count = getCurrentCount();

        return count < maxCount;
    }

    private int getCurrentCount() {
        return orderResultRset.size();
    }
}
