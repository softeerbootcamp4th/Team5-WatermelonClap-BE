package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class OrderResultQueryService {
    private final OrderResultRepository orderResultRepository;

    private final int maxCount;

    public OrderResultQueryService(OrderResultRepository orderResultRepository) {
        this.orderResultRepository = orderResultRepository;
        this.maxCount= 10;
    }

    public boolean canMakeOrderEventApply(){
        int count = getCurrentCount();
        return count < maxCount;
    }

    private int getCurrentCount() {
        return orderResultRepository.findAll().size();
    }

}
