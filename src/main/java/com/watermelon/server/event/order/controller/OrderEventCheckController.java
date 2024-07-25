package com.watermelon.server.event.order.controller;

import com.watermelon.server.event.order.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.event.order.service.OrderEventCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * 선착순 참여 가능 여부를 응답하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderEventCheckController {

    private final OrderEventCheckService orderEventCheckService;

    @GetMapping("/event/order/{eventId}/{quizId}")
    public ResponseEntity<ResponseOrderEventResultDto> getOrderEventResult(
            @RequestHeader("ApplyTicket") String ticket,
            @PathVariable Long eventId,
            @PathVariable Long quizId,
            @Param("answer") String answer
    ) {
        return new ResponseEntity<>(
                orderEventCheckService.getOrderEventResult(ticket, eventId, quizId, answer),
                HttpStatus.OK
        );
    }

}
