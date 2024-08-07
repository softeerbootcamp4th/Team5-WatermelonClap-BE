package com.watermelon.server.event.order.service;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventWinner;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.error.WrongPhoneNumberFormatException;
import com.watermelon.server.event.order.repository.OrderEventWinnerRepository;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderEventWinnerService {
    private static final Logger log = LoggerFactory.getLogger(OrderEventWinnerService.class);
    private final OrderEventWinnerRepository orderEventWinnerRepository;
    private final ApplyTokenProvider applyTokenProvider;

    @Transactional
    public OrderEventWinner makeWinner(
            OrderEvent orderEvent,
            OrderEventWinnerRequestDto orderEventWinnerRequestDto,
            String applyAnswer,
            String applyTicket)
            throws ApplyTicketWrongException, WrongPhoneNumberFormatException {
        if(!orderEventWinnerRequestDto.isPhoneNumberValid()) throw new WrongPhoneNumberFormatException();
        JwtPayload payload = applyTokenProvider.verifyToken(applyTicket, String.valueOf(orderEvent.getId()));
        OrderEventWinner orderEventWinner = OrderEventWinner.makeWinner(orderEvent
                , orderEventWinnerRequestDto
        ,applyAnswer);
        //ApplyTicket  payload에서 applyAnswer를 담도록 하여야함
        return orderEventWinnerRepository.save(orderEventWinner);
    }
}
