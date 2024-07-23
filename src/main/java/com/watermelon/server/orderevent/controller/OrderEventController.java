package com.watermelon.server.orderevent.controller;


import com.watermelon.server.orderevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.orderevent.dto.response.ResponseOrderEventDto;
import com.watermelon.server.orderevent.service.OrderEventCommandService;
import com.watermelon.server.orderevent.service.OrderEventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/order")
public class OrderEventController {

    private final OrderEventQueryService orderEventQueryService;
    private final OrderEventCommandService orderEventCommandService;

    @GetMapping
    public List<ResponseOrderEventDto> getOrderEvents(){
        return orderEventQueryService.getOrderEvents();
    }
//    @PostMapping(path = "/apply")
//    public ResponseQuizResultDto applyFifoEvent(@RequestBody RequestAnswerDto requestAnswerDto){
//        return fifoEventService.applyFifoEvent(requestAnswerDto);
//    }

    @GetMapping(path = "/{eventId}")
    public ResponseOrderEventDto getOrderEvent(@PathVariable Long orderEventId){
        return orderEventQueryService.getOrderEvent(orderEventId);
    }

    @PostMapping
    public void makeEvent(RequestOrderEventDto requestOrderEventDto){
        orderEventCommandService.makeEvent(requestOrderEventDto);
    }

}
