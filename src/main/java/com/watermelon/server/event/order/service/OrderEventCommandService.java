package com.watermelon.server.event.order.service;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.domain.OrderEventCheckService;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.result.service.OrderResultCommandService;
import com.watermelon.server.event.order.result.service.OrderResultQueryService;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;
    private final ApplyTokenProvider applyTokenProvider;
    private final OrderEventWinnerService orderEventWinnerService;
    private final OrderResultCommandService orderResultCommandService;
    private final OrderEventCheckService orderEventCheckService;


    @Transactional
    public ResponseApplyTicketDto makeApplyTicket(RequestAnswerDto requestAnswerDto , Long orderEventId, Long quizId) throws WrongOrderEventFormatException, NotDuringEventPeriodException {
        orderEventCheckService.checkingInfoErrors(orderEventId,quizId);
        // 퀴즈 틀릴 시에
        if(!orderEventCheckService.isAnswerCorrect(requestAnswerDto.getAnswer()))
        {
            return ResponseApplyTicketDto.wrongAnswer();
        }
        //토큰 생성
        String applyTicketToken = applyTokenProvider.createTokenByQuizId(JwtPayload.from(String.valueOf(orderEventId )));
        // 선착순 마감이 아닐시에
        if(orderResultCommandService.isOrderResultFullElseMake(applyTicketToken)) return ResponseApplyTicketDto.applySuccess(applyTicketToken);
        return ResponseApplyTicketDto.fullApply();

        //저장 할시에 확실하게 돌려주어야함 - 하지만 돌려주지 못 할시에는 어떻게?( 로그인이 안 되어있음)
    }

    public void makeOrderEventWinner(String applyTicket, Long eventId, OrderEventWinnerRequestDto orderEventWinnerRequestDto) throws ApplyTicketWrongException, WrongOrderEventFormatException {
        JwtPayload payload = applyTokenProvider.verifyToken(applyTicket, String.valueOf(eventId));
        OrderEvent orderEvent = orderEventRepository.findById(eventId).orElseThrow(WrongOrderEventFormatException::new);
        orderEventWinnerService.makeWinner(orderEvent, orderEventWinnerRequestDto,"payLoad.applyAnswer");
    }

}
