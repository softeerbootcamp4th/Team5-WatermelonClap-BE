package com.watermelon.server.event.order.service;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.result.service.OrderResultCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;
    private final OrderEventWinnerService orderEventWinnerService;
    private final OrderResultCommandService orderResultCommandService;
    private final OrderEventCheckService orderEventCheckService;

    public OrderEventCommandService(
            OrderEventRepository orderEventRepository,
            OrderEventWinnerService orderEventWinnerService,
            OrderResultCommandService orderResultCommandService,
            OrderEventCheckService orderEventCheckService) {
        this.orderEventRepository = orderEventRepository;
        this.orderEventWinnerService = orderEventWinnerService;
        this.orderResultCommandService = orderResultCommandService;
        this.orderEventCheckService = orderEventCheckService;
        setOrderEventCheckService();
    }
    @Transactional
    public ResponseApplyTicketDto makeApplyTicket(RequestAnswerDto requestAnswerDto , Long orderEventId, Long quizId) throws WrongOrderEventFormatException, NotDuringEventPeriodException {
        orderEventCheckService.checkingInfoErrors(orderEventId,quizId);
        // 퀴즈 틀릴 시에
        if(!orderEventCheckService.isAnswerCorrect(requestAnswerDto.getAnswer()))
        {
            return ResponseApplyTicketDto.wrongAnswer();
        }
        return orderResultCommandService.isOrderResultFullElseMake(orderEventId);
    }
    public void makeOrderEventWinner(String applyTicket, Long eventId, OrderEventWinnerRequestDto orderEventWinnerRequestDto) throws ApplyTicketWrongException, WrongOrderEventFormatException {
        OrderEvent orderEvent = orderEventRepository.findById(eventId).orElseThrow(WrongOrderEventFormatException::new);
        orderEventWinnerService.makeWinner(orderEvent, orderEventWinnerRequestDto,"payLoad.applyAnswer",applyTicket);
    }

    public void setOrderEventCheckService(){
        //현재 OrderEvent의 상태를 주기적으로 변경
        List<OrderEvent> orderEvent = orderEventRepository.findAll();
        if(orderEvent.isEmpty()) return; // 이벤트 없을시 스킵
        OrderEvent currentOrderEvent = orderEvent.get(0); // 여기서 현재 이벤트를 검증해야함
        this.orderEventCheckService.refreshInforMation(currentOrderEvent);
    }


}
