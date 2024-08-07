package com.watermelon.server.event.order.controller;


import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.PhoneNumberNotExistException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.service.OrderEventCommandService;
import com.watermelon.server.event.order.service.OrderEventQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderEventController {

    private final OrderEventQueryService orderEventQueryService;
    private final OrderEventCommandService orderEventCommandService;

    @GetMapping(path = "/event/order")
    public List<ResponseOrderEventDto> getOrderEvents(){

        return orderEventQueryService.getOrderEvents();
    }
//    @PostMapping(path = "/apply")
//    public ResponseQuizResultDto applyFifoEvent(@RequestBody RequestAnswerDto requestAnswerDto){
//        return fifoEventService.applyFifoEvent(requestAnswerDto);
//    }

    @GetMapping(path = "/event/order/{eventId}")
    public ResponseOrderEventDto getOrderEvent(@PathVariable("eventId") Long orderEventId) throws WrongOrderEventFormatException {
        return orderEventQueryService.getOrderEvent(orderEventId);
    }

    @PostMapping(path = "/event/order/{eventId}/{quizId}")
    public ResponseApplyTicketDto makeApplyTicket(@RequestBody RequestAnswerDto requestAnswerDto,
                                                  @PathVariable("eventId") Long orderEventId,
                                                  @PathVariable("quizId") Long quizId)
            throws WrongOrderEventFormatException, NotDuringEventPeriodException {

        return orderEventCommandService.makeApplyTicket(requestAnswerDto,orderEventId,quizId);
    }


    @PostMapping(path = "/event/order/{eventId}/{quizId}/apply")
    public void makeApply(@RequestHeader("ApplyTicket") String applyTicket,
                          @PathVariable("eventId") Long eventId,
                          @PathVariable("quizId") Long quizId,
                          @RequestBody OrderEventWinnerRequestDto orderEventWinnerRequestDto)
            throws ApplyTicketWrongException
            , WrongOrderEventFormatException
            , PhoneNumberNotExistException {
        orderEventCommandService.makeOrderEventWinner(applyTicket,eventId,orderEventWinnerRequestDto);
    }

    @ExceptionHandler(PhoneNumberNotExistException.class)
    public ResponseEntity<String> handlePhoneNumberNotExistException(PhoneNumberNotExistException phoneNumberNotExistException){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(phoneNumberNotExistException.getMessage());
    }
    @ExceptionHandler(WrongOrderEventFormatException.class)
    public ResponseEntity<String> handleWrongOrderEventFormatException(WrongOrderEventFormatException wrongOrderEventFormatException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(wrongOrderEventFormatException.getMessage());
    }
    @ExceptionHandler(ApplyTicketWrongException.class)
    public ResponseEntity<String> handleApplyTicketWrongException(ApplyTicketWrongException applyTicketWrongException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(applyTicketWrongException.getMessage());
    }
}
