package com.watermelon.server.orderevent.controller;

import com.watermelon.server.orderevent.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.orderevent.service.QuizCheckService;
import com.watermelon.server.orderevent.service.OrderEventCheckService;
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
    private final QuizCheckService quizCheckService;

    @GetMapping("/event/order/{eventId}/{quizId}")
    public ResponseEntity<ResponseOrderEventResultDto> getOrderEventResult(
            @RequestHeader("ApplyTicket") String ticket,
            @PathVariable Long eventId,
            @PathVariable Long quizId,
            @Param("answer") String answer
    ) {

        if (!quizCheckService.isCorrect(quizId, answer)) {
            //정답이 틀림
            return new ResponseEntity<>(
                    ResponseOrderEventResultDto.builder()
                            .result(ResponseOrderEventResultDto.Status.WRONG.getValue())
                            .build(), HttpStatus.OK);
        }

        if (orderEventCheckService.isAble(ticket)) {
            //선착순 참여 가능
            return new ResponseEntity<>(
                    ResponseOrderEventResultDto.builder()
                            .result(ResponseOrderEventResultDto.Status.SUCCESS.getValue())
                            .build(), HttpStatus.OK);

        }

        //선착순 마감
        return new ResponseEntity<>(
                ResponseOrderEventResultDto.builder()
                        .result(ResponseOrderEventResultDto.Status.CLOSED.getValue())
                        .build(), HttpStatus.OK);

    }

}
