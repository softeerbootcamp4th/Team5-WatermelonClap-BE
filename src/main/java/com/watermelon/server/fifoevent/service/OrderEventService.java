package com.watermelon.server.fifoevent.service;


import com.watermelon.server.fifoevent.domain.OrderEvent;
import com.watermelon.server.fifoevent.domain.Quiz;
import com.watermelon.server.fifoevent.dto.request.RequestAnswerDto;
import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizDto;
import com.watermelon.server.fifoevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.fifoevent.repository.OrderEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderEventRepository orderEventRepository;
    private final QuizRepository quizRepository;


    public ResponseQuizDto getQuiz(){
        Optional<OrderEvent> fifoEventOptional = orderEventRepository.findByDateBetween(LocalDateTime.now());
        if(!fifoEventOptional.isPresent()){return null;}
        OrderEvent orderEvent = fifoEventOptional.get();
        return ResponseQuizDto.from(orderEvent.getQuiz(), orderEvent.getId());
    }
    public void makeEvent(RequestOrderEventDto requestOrderEventDto){
        System.out.println(requestOrderEventDto);
        OrderEvent newOrderEvent = OrderEvent.makeFifoEvent(requestOrderEventDto);
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
