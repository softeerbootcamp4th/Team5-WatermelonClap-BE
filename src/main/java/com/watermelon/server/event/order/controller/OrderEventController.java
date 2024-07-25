package com.watermelon.server.event.order.controller;


import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.service.OrderEventCommandService;
import com.watermelon.server.event.order.service.OrderEventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderEventController {

    private final OrderEventQueryService orderEventQueryService;
    private final OrderEventCommandService orderEventCommandService;

    @GetMapping(path = "/event/order")
    public List<ResponseOrderEventDto> getOrderEvents(){
        return orderEventQueryService.getOrderEvents();
    }
//    @PostMapping(path = "/apply")
//    public ResponseQuizResultDto applyFifoEvent(@RequestBody RequestAnswerDto requestAnswerDto){
//        return fifoEventService.applyFifoEvent(requestAnswerDto);
//    }

    @GetMapping(path = "/event/order/{eventId}")
    public ResponseOrderEventDto getOrderEvent(@PathVariable Long orderEventId){
        return orderEventQueryService.getOrderEvent(orderEventId);
    }

    @PostMapping(path = "/event/order/{eventId}/{quizId}")
    public ResponseApplyTicketDto makeApplyTicket(@RequestBody RequestAnswerDto requestAnswerDto,
                                                  @PathVariable Long orderEventId,
                                                  @PathVariable Long quizId)
            throws WrongOrderEventFormatException, NotDuringEventPeriodException {

        return orderEventCommandService.makeApplyTicket(requestAnswerDto,orderEventId,quizId);
    }
    @PostMapping("/event/order")
    public void makeEvent(RequestOrderEventDto requestOrderEventDto){
        orderEventCommandService.makeEvent(requestOrderEventDto);
    }

}
