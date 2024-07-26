package com.watermelon.server.event.order.result.service;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.dto.request.OrderResultRequestDto;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderResultCommandService {
    private final OrderResultRepository orderResultRepository;
    private final ApplyTokenProvider applyTokenProvider;

//    public OrderResult makeOrderEventApply(String applyToken){
//        OrderResult orderResult = OrderResult.makeOrderEventApply(applyToken);
//        return orderResultRepository.save(orderResult);
//    }
    public void makeApply(String applyTicket, String eventId, OrderResultRequestDto orderResultRequestDto) throws ApplyTicketWrongException {
        JwtPayload payload = applyTokenProvider.verifyToken(applyTicket,eventId);
        OrderResult orderResult = OrderResult.makeOrderEventApply(applyTicket,payload.getEventId(),orderResultRequestDto);
        orderResultRepository.save(orderResult);
    }
}
