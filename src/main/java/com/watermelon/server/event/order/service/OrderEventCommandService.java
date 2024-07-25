package com.watermelon.server.event.order.service;


import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.repository.QuizRepository;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import com.watermelon.server.event.order.result.service.OrderResultCommandService;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;
    private final OrderResultRepository orderResultRepository;
    private final ApplyTokenProvider applyTokenProvider;
    private final OrderResultCommandService orderResultCommandService;

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
    @Transactional
    public ResponseApplyTicketDto makeApplyTicket(RequestAnswerDto requestAnswerDto , Long orderEventId, Long quizId) throws WrongOrderEventFormatException, NotDuringEventPeriodException {

        OrderEvent orderEvent = orderEventRepository.findByIdAndQuizId(orderEventId,quizId).orElseThrow(WrongOrderEventFormatException::new);

        // 기간이 아닐시에
        if(!orderEvent.isTimeInEventTime(LocalDateTime.now())) throw new NotDuringEventPeriodException();
        // 퀴즈 틀릴 시에
        Quiz quiz = orderEvent.getQuiz();
        if(!quiz.isCorrect(requestAnswerDto.getAnswer())) return ResponseApplyTicketDto.wrongAnswer();



        //토큰 생성
        String applyToken = applyTokenProvider.createTokenByQuizId(JwtPayload.from(String.valueOf(quizId)));


        orderResultCommandService.makeOrderEventApply(applyToken);

        return ResponseApplyTicketDto.from(applyToken);

    }

}
