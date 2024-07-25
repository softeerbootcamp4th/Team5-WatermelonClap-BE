package com.watermelon.server.event.order.result.service;


import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderResultCommandService {
    private final OrderResultRepository orderResultRepository;

    public void makeOrderEventApply(String applyToken){
        OrderResult orderResult = OrderResult.makeOrderEventApply(applyToken);
        orderResultRepository.save(orderResult);
    }

}
