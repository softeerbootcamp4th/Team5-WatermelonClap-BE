package com.watermelon.server.event.order.service;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.domain.OrderEventWinner;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.repository.OrderEventWinnerRepository;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import com.watermelon.server.event.order.result.service.OrderResultCommandService;
import com.watermelon.server.event.order.result.service.OrderResultQueryService;
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
    private final OrderResultQueryService orderResultQueryService;
    private final ApplyTokenProvider applyTokenProvider;
    private final OrderEventWinnerService orderEventWinnerService;
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

        OrderEvent orderEvent = checkOrderEventNotError(orderEventId, quizId);

        // 퀴즈 틀릴 시에
        Quiz quiz = orderEvent.getQuiz();
        if(!quiz.isCorrect(requestAnswerDto.getAnswer())) return ResponseApplyTicketDto.wrongAnswer();





        //토큰 생성
        String applyTicketToken = applyTokenProvider.createTokenByQuizId(JwtPayload.from(String.valueOf(orderEventId )));

        //(선착순 마감 확인,저장은 하나의 transaction 단위로 걸어야함)
        // 선착순 마감시에
        if(!orderResultQueryService.isOrderApplyNotFull()) return ResponseApplyTicketDto.fullApply();
        orderResultCommandService.makeOrderEventApply(applyTicketToken);

        //저장 할시에 확실하게 돌려주어야함 - 하지만 돌려주지 못 할시에는 어떻게?( 로그인이 안 되어있음)
        return ResponseApplyTicketDto.applySuccess(applyTicketToken);
    }

    private OrderEvent checkOrderEventNotError(Long orderEventId, Long quizId) throws WrongOrderEventFormatException, NotDuringEventPeriodException {
        //id가 다를 시에
        OrderEvent orderEvent = orderEventRepository.findByIdAndQuizId(orderEventId, quizId).orElseThrow(WrongOrderEventFormatException::new);
        // 기간이 아닐시에
        if(!orderEvent.isTimeInEventTime(LocalDateTime.now())) throw new NotDuringEventPeriodException();
        return orderEvent;
    }

    public void makeOrderEventWinner(String applyTicket, Long eventId, OrderEventWinnerRequestDto orderEventWinnerRequestDto) throws ApplyTicketWrongException, WrongOrderEventFormatException {
        JwtPayload payload = applyTokenProvider.verifyToken(applyTicket, String.valueOf(eventId));
        OrderEvent orderEvent = orderEventRepository.findById(eventId).orElseThrow(WrongOrderEventFormatException::new);
        orderEventWinnerService.makeWinner(orderEvent, orderEventWinnerRequestDto);
    }

}
