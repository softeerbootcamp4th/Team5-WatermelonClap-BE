package com.watermelon.server.orderevent.service;


import com.watermelon.server.orderevent.domain.OrderEvent;
import com.watermelon.server.orderevent.domain.Quiz;
import com.watermelon.server.orderevent.dto.request.RequestAnswerDto;
import com.watermelon.server.orderevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.orderevent.dto.response.ResponseQuizResultDto;
import com.watermelon.server.orderevent.repository.OrderEventRepository;
import com.watermelon.server.orderevent.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;
    private final QuizRepository quizRepository;
    @Transactional
    public void changeOrderStatusByTime(){
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        orderEvents.forEach(orderEvent -> {orderEvent.changeOrderEventStatusByTime(LocalDateTime.now());});
    }

    @Transactional
    public OrderEvent makeEvent(RequestOrderEventDto requestOrderEventDto){
        OrderEvent newOrderEvent = OrderEvent.makeOrderEvent(requestOrderEventDto);
        return orderEventRepository.save(newOrderEvent);
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
