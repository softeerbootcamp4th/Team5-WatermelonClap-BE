package com.watermelon.server.fifoevent.controller;


import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/fifo")
public class OrderEventController {

    private final OrderEventService orderEventService;

    @GetMapping
    public ResponseQuizDto getQuiz(){
        return orderEventService.getQuiz();
    }
//    @PostMapping(path = "/apply")
//    public ResponseQuizResultDto applyFifoEvent(@RequestBody RequestAnswerDto requestAnswerDto){
//        return fifoEventService.applyFifoEvent(requestAnswerDto);
//    }





    @PostMapping
    public void makeEvent(RequestOrderEventDto requestOrderEventDto){
        orderEventService.makeEvent(requestOrderEventDto);
    }

}
