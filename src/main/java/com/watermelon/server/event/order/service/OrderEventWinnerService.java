package com.watermelon.server.event.order.service;


import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventWinner;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.repository.OrderEventWinnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventWinnerService {
    private final OrderEventWinnerRepository orderEventWinnerRepository;
    public OrderEventWinner makeWinner(OrderEvent orderEvent, OrderEventWinnerRequestDto orderEventWinnerRequestDto){
        OrderEventWinner orderEventWinner = OrderEventWinner.makeWinner(orderEvent, orderEventWinnerRequestDto);
        return orderEventWinnerRepository.save(orderEventWinner);
    }
}
