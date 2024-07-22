package com.watermelon.server.fifoevent.service;


import com.watermelon.server.fifoevent.domain.OrderEvent;
import com.watermelon.server.fifoevent.domain.OrderEventStatus;
import com.watermelon.server.fifoevent.domain.Quiz;
import com.watermelon.server.fifoevent.dto.request.RequestAnswerDto;
import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseOrderEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.fifoevent.repository.OrderEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderEventRepository orderEventRepository;
    private final QuizRepository quizRepository;


    public List<ResponseOrderEventDto> getOrderEvents(){
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();
        for (OrderEvent orderEvent : orderEvents) {
            responseOrderEventDtos.add(ResponseOrderEventDto.from(orderEvent, OrderEventStatus.UPCOMING));
        }
        return responseOrderEventDtos;
    }


    public void makeEvent(RequestOrderEventDto requestOrderEventDto){
        OrderEvent newOrderEvent = OrderEvent.makeOrderEvent(requestOrderEventDto);
        orderEventRepository.save(newOrderEvent);
    }
    public ResponseQuizResultDto applyFifoEvent(RequestAnswerDto requestAnswerDto){
        Optional<OrderEvent> fifoEventOptional = orderEventRepository.findById(requestAnswerDto.getFifoEventId());
        OrderEvent orderEvent = fifoEventOptional.get();

        // 시간 틀림
        if(!orderEvent.isTimeInEventTime(LocalDateTime.now())){
            return ResponseQuizResultDto.eventTimeOutResult();
        }
        Quiz quiz = orderEvent.getQuiz();


        //정답 틀림
        if(!quiz.isCorrect(requestAnswerDto.getAnswer())){
            return ResponseQuizResultDto.wrongAnswerResult();
        }

        //선착순 틀림
        if(!orderEvent.isWinnerAddable()){
            return ResponseQuizResultDto.noMoreWinnerResult();
        }

        //선착순 토큰 발급
        orderEvent.addWinner();
        return ResponseQuizResultDto.answerRightResult(UUID.randomUUID().toString());


    }

}
