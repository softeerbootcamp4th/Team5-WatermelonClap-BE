package com.watermelon.server.admin.service;


import com.watermelon.server.event.order.domain.OrderEvent;

import com.watermelon.server.event.order.domain.OrderEventWinner;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventWinnerDto;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminOrderEventService {
    private final OrderEventRepository orderEventRepository;


    @Transactional(readOnly = true)
    public List<ResponseOrderEventDto> getOrderEventsForAdmin() {
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();
        orderEvents.forEach(orderEvent -> responseOrderEventDtos.add(
                ResponseOrderEventDto.forAdmin(orderEvent)));
        return responseOrderEventDtos;
    }

    @Transactional(readOnly = true)
    public List<ResponseOrderEventWinnerDto> getOrderEventWinnersForAdmin(Long eventId) throws WrongOrderEventFormatException {
        OrderEvent orderEvent = orderEventRepository.findByIdFetchWinner(eventId).orElseThrow(WrongOrderEventFormatException::new);
        List< OrderEventWinner> orderEventWinners = orderEvent.getOrderEventWinner();
        List<ResponseOrderEventWinnerDto> responseOrderEventWinnerDtos = new ArrayList<>();
        orderEventWinners.forEach(orderEventWinner-> responseOrderEventWinnerDtos.add(
                ResponseOrderEventWinnerDto.forAdmin(orderEventWinner)
        ));
        return responseOrderEventWinnerDtos;
    }

    @Transactional
    public OrderEvent makeOrderEvent(RequestOrderEventDto requestOrderEventDto){
        OrderEvent newOrderEvent = OrderEvent.makeOrderEvent(requestOrderEventDto);
        return orderEventRepository.save(newOrderEvent);
    }
}