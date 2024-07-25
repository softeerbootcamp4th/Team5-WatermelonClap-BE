package com.watermelon.server.event.order.service;

import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderEventCommandServiceTest {

    @Mock
    private OrderEventRepository orderEventRepository;
    @InjectMocks
    private OrderEventCommandService orderEventCommandService;


    private Long quizId=1L;
    private Long eventId =1L;
    private String answer ="answer";
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;



    @BeforeEach
    @DisplayName("응모 생성")
     void makeOrderEvent(){
        LocalDateTime now = LocalDateTime.now();
        startDateTime = now;
        endDateTime = now.plusSeconds(3);
        OrderEvent orderEvent = OrderEvent.builder()
                .quiz(Quiz.builder().answer(answer).build())
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();

        when(orderEventRepository.findByIdAndQuizId(eventId,quizId)).thenReturn(Optional.ofNullable(orderEvent));


    }

    @Test
    @DisplayName("정상 응모")
    void makeApplyTicket() throws NotDuringEventPeriodException, WrongOrderEventFormatException {
        ResponseApplyTicketDto responseApplyTicketDto = orderEventCommandService.makeApplyTicket(RequestAnswerDto.builder()
                .answer(answer+"++")
                .build(),eventId,quizId);
        Assertions.assertThat(responseApplyTicketDto).isNotNull();
    }
    @Test
    @DisplayName("선착순 응모 - 에러(존재하지 않음)")
    void makeApplyTicketError() {
        Assertions.assertThatThrownBy(()->
                orderEventCommandService.makeApplyTicket(RequestAnswerDto.builder()
                        .answer(answer+"++")
                        .build(),eventId+1,quizId)
                ).isInstanceOf(WrongOrderEventFormatException.class);
    }
    @Test
    @DisplayName("선착순 응모 - 에러(기간 오류)")
    void makeApplyTicketWrong()  {

        OrderEvent wrongTimeOrderEvent = OrderEvent.builder()
                .quiz(Quiz.builder().answer(answer).build())
                .startDate(startDateTime)
                .endDate(endDateTime.minusSeconds(100))
                .build();
        when(orderEventRepository.findByIdAndQuizId(eventId,quizId)).thenReturn(Optional.ofNullable(wrongTimeOrderEvent));
        Assertions.assertThatThrownBy(()->
                orderEventCommandService.makeApplyTicket(RequestAnswerDto.builder()
                        .answer(answer+"++")
                        .build(),eventId,quizId)
        ).isInstanceOf(NotDuringEventPeriodException.class);
    }

}