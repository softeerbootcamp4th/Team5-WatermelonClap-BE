package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class OrderResultQueryService {
    private final OrderResultRepository orderResultRepository;

    @Getter
    private final int maxCount;

    public OrderResultQueryService(OrderResultRepository orderResultRepository) {
        this.orderResultRepository = orderResultRepository;
        this.maxCount= 100;
    }

    public boolean isOrderApplyNotFull(){
        int count = getCurrentCount();
        return count < maxCount;
    }

    private int getCurrentCount() {
        return orderResultRepository.findAll().size();
    }

}
