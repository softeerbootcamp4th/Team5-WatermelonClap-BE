package com.watermelon.server.fifoevent.controller;


import com.watermelon.server.fifoevent.domain.FifoEvent;
import com.watermelon.server.fifoevent.dto.request.RequestAnswerDto;
import com.watermelon.server.fifoevent.dto.request.RequestFiFoEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.fifoevent.service.FifoEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/fifo")
public class FifoEventController {

    private final FifoEventService fifoEventService;

    @GetMapping
    public ResponseQuizDto getQuiz(){
        return fifoEventService.getQuiz();
    }
//    @PostMapping(path = "/apply")
//    public ResponseQuizResultDto applyFifoEvent(@RequestBody RequestAnswerDto requestAnswerDto){
//        return fifoEventService.applyFifoEvent(requestAnswerDto);
//    }





    @PostMapping
    public void makeEvent(RequestFiFoEventDto requestFiFoEventDto){
        fifoEventService.makeEvent(requestFiFoEventDto);
    }

}
