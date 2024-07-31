package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.result.domain.OrderResult;
import lombok.Getter;
import org.redisson.api.RSet;
import org.springframework.stereotype.Service;

@Service
public class OrderResultQueryService {
    private final RSet<OrderResult> orderResultRset;

    @Getter
    private int availableTicket;

    public OrderResultQueryService(RSet orderResultRset) {
        this.orderResultRset = orderResultRset;
        this.availableTicket = 100;
    }

    public boolean isOrderApplyNotFull(){
        int count = getCurrentCount();
        return availableTicket-count>0;
    }

    private int getCurrentCount() {
        return orderResultRset.size();
    }
}
