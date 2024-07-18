package com.watermelon.server.fifoevent.controller;


import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.service.FifoEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/fifo")
public class FifoEventController {

    private final FifoEventService fifoEventService;



    @GetMapping
    public ResponseQuizDto getQuiz(){
        return fifoEventService.getQuiz();

    }
}
