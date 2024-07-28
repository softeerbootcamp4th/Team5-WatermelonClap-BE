package com.watermelon.server.admin.service;


import com.watermelon.server.event.order.domain.OrderEvent;

import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
    private final OrderEventRepository orderEventRepository;


    @Transactional(readOnly = true)
    public List<ResponseOrderEventDto> getOrderEventsForAdmin() {
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();
        orderEvents.forEach(orderEvent -> responseOrderEventDtos.add(ResponseOrderEventDto.forAdmin(orderEvent)));
        return responseOrderEventDtos;
    }

    @Transactional
    public OrderEvent makeOrderEvent(RequestOrderEventDto requestOrderEventDto){
        OrderEvent newOrderEvent = OrderEvent.makeOrderEvent(requestOrderEventDto);
        return orderEventRepository.save(newOrderEvent);
    }
}
