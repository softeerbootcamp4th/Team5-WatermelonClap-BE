package com.watermelon.server.event.order.service;

import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.dto.request.RequestAnswerDto;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.error.NotDuringEventPeriodException;
import com.watermelon.server.event.order.error.WrongOrderEventFormatException;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.result.service.OrderResultCommandService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderEventCommandServiceTest {

    @Mock
    private OrderEventRepository orderEventRepository;
    @Mock
    private OrderEventCheckService orderEventCheckService;
    @Mock
    private OrderResultCommandService orderResultCommandService;
    @InjectMocks
    private OrderEventCommandService orderEventCommandService;


    private Long quizId=1L;
    private Long eventId =1L;
    private String answer ="answer";
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public String applyToken = "applyToken";
    OrderEvent orderEvent;

    @BeforeEach
    @DisplayName("응모 생성")
     void makeOrderEventWithOutImageWithOutImage(){
        LocalDateTime now = LocalDateTime.now();
        startDateTime = now;
        endDateTime = now.plusSeconds(10);
        orderEvent = OrderEvent.builder()
                .quiz(Quiz.builder().answer(answer).build())
                .startDate(startDateTime)
                .endDate(endDateTime)
                .build();
    }

    @Test
    @DisplayName("정상 응모")
    void makeApplyTicket() throws NotDuringEventPeriodException, WrongOrderEventFormatException {

        when(orderEventCheckService.isAnswerCorrect(any())).thenReturn(true);
        when(orderResultCommandService.isOrderResultFullElseMake(any())).thenReturn(ResponseApplyTicketDto.applySuccess(applyToken));
        Assertions.assertThat(orderEventCommandService.makeApplyTicket(RequestAnswerDto.makeWith(answer),1L,1L).getResult())
                .isEqualTo(ApplyTicketStatus.SUCCESS.name());

    }

    @Test
    @DisplayName("선착순 응모 -정답 틀림")
    void makeApplyTicketWrongAnswer() throws NotDuringEventPeriodException, WrongOrderEventFormatException {
        when(orderEventCheckService.isAnswerCorrect(any())).thenReturn(false);
        Assertions.assertThat(orderEventCommandService.makeApplyTicket(RequestAnswerDto.makeWith(answer),1L,1L).getResult())
                .isEqualTo(ApplyTicketStatus.WRONG.name());
    }


    @Test
    @DisplayName("선착순 응모 - 에러(존재하지 않음)")
    void makeApplyTicketIdError() throws NotDuringEventPeriodException, WrongOrderEventFormatException {
        Mockito.doThrow(WrongOrderEventFormatException.class).when(orderEventCheckService).checkingInfoErrors(any(),any());
        Assertions.assertThatThrownBy(()->
                orderEventCommandService.makeApplyTicket(RequestAnswerDto.builder()
                        .answer(answer)
                        .build(),eventId,quizId)
                ).isInstanceOf(WrongOrderEventFormatException.class);
    }
    @Test
    @DisplayName("선착순 응모 - 에러(기간 오류)")
    void makeApplyTicketTimeError() throws NotDuringEventPeriodException, WrongOrderEventFormatException {
        Mockito.doThrow(NotDuringEventPeriodException.class).when(orderEventCheckService).checkingInfoErrors(any(),any());
        Assertions.assertThatThrownBy(()->
                orderEventCommandService.makeApplyTicket(RequestAnswerDto.builder()
                        .answer(answer)
                        .build(),eventId,quizId)
        ).isInstanceOf(NotDuringEventPeriodException.class);
    }


    @Test
    @DisplayName("선착순 당첨자 등록")
    void makeOrderEventWithOutImageWithOutImageWinner() {
    }
}