package com.watermelon.server.event.order.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.error.ApplyTicketWrongException;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.dto.request.*;
import com.watermelon.server.event.order.dto.response.ResponseApplyTicketDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.error.WrongPhoneNumberFormatException;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.service.OrderEventCommandService;
import com.watermelon.server.event.order.service.OrderEventQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static reactor.core.publisher.Mono.when;


@WebMvcTest(OrderEventController.class)
class OrderEventControllerTest extends ControllerTest {
    @MockBean
    private OrderEventQueryService orderEventQueryService;
    @MockBean
    private OrderEventCommandService orderEventCommandService;

    @MockBean
    private OrderEventRepository orderEventRepository;

    private ResponseOrderEventDto openOrderEventResponse;
    private ResponseOrderEventDto soonOpenResponse;
    private ResponseOrderEventDto unOpenResponse;
    private OrderEvent soonOpenOrderEvent;
    private OrderEvent openOrderEvent;
    private OrderEvent unOpenOrderEvent;
    private List<OrderEvent> orderEvents = new ArrayList<>();
    private List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();

    @BeforeEach
    void setUp(){
        openOrderEvent = OrderEvent.makeOrderEventWithInputIdForDocumentation(
                RequestOrderEventDto.makeForTestOpened(
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                ),1L
        );
        soonOpenOrderEvent = OrderEvent.makeOrderEventWithInputIdForDocumentation(
                RequestOrderEventDto.makeForTestOpenAfter1SecondCloseAfter3Second
                        (
                                RequestQuizDto.makeForTest(),
                                RequestOrderRewardDto.makeForTest()
                        ),2L
        );
        unOpenOrderEvent = OrderEvent.makeOrderEventWithInputIdForDocumentation(
                RequestOrderEventDto.makeForTestOpen10HoursLater
                        (
                                RequestQuizDto.makeForTest(),
                                RequestOrderRewardDto.makeForTest()
                        ),3L
        );
        openOrderEvent.setOrderEventStatus(OrderEventStatus.OPEN);

        orderEvents.add(openOrderEvent);
        orderEvents.add(soonOpenOrderEvent);
        orderEvents.add(unOpenOrderEvent);

        openOrderEventResponse = ResponseOrderEventDto.forUser(openOrderEvent);
        soonOpenResponse = ResponseOrderEventDto.forUser(soonOpenOrderEvent);
        unOpenResponse = ResponseOrderEventDto.forUser(unOpenOrderEvent);

        responseOrderEventDtos.add(openOrderEventResponse);
        responseOrderEventDtos.add(soonOpenResponse);
        responseOrderEventDtos.add(unOpenResponse);

    }
    @Test
    @DisplayName("[DOC] 선착순 이벤트 목록을 가져온다")
    void getOrderEvents() throws Exception {
        final String PATH = "/event/order";
        final String DOCUMENT_NAME ="event/order";
        Mockito.when(orderEventQueryService.getOrderEvents()).thenReturn(responseOrderEventDtos);

        mockMvc.perform(RestDocumentationRequestBuilders.get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseOrderEventDtos)))
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
                        resourceSnippet("선착순 이벤트 목록 조회")));
    }
    @Test
    @DisplayName("[DOC] 특정 선착순 이벤트를 가져온다")
    void getOrderEvent() throws Exception {
        final String PATH = "/event/order/{eventId}";
        final String DOCUMENT_NAME ="event/order/{eventId}";
        System.out.println(openOrderEventResponse);
        Mockito.when(orderEventQueryService.getOrderEvent(openOrderEventResponse.getEventId())).thenReturn(openOrderEventResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get(PATH, openOrderEventResponse.getEventId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(openOrderEventResponse)))
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
                        resourceSnippet("특정 선착순 이벤트 조회")));
    }

    @Test
    @DisplayName("[DOC] 선착순 이벤트 번호 제출")
    void makeApplyTicket() throws Exception {
        final String Path = "/event/order/{eventId}/{quizId}/apply";
        final String DOCUMENT_NAME ="event/order/{eventId}/{quizId}/apply";


        String applyTicket = "applyTicket";

        mockMvc.perform(RestDocumentationRequestBuilders.post(Path,
                openOrderEventResponse.getEventId(),
                openOrderEventResponse.getQuiz().getQuizId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234")))
                                .header("ApplyTicket",applyTicket))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
                        resourceSnippet("선착순 퀴즈 번호 제출")));

//        Mockito.doThrow(WrongPhoneNumberFormatException.class).when(orderEventCommandService).makeOrderEventWinner(any(),any(),any());
//        mockMvc.perform(RestDocumentationRequestBuilders.post(Path,
//                                openOrderEventResponse.getEventId(),
//                                openOrderEventResponse.getQuiz().getQuizId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234")))
//                        .header("ApplyTicket",applyTicket))
//                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
//                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
//                        resourceSnippet("선착순 퀴즈 번호 제출")));
//
//        Mockito.doThrow(ApplyTicketWrongException.class).when(orderEventCommandService).makeOrderEventWinner(any(),any(),any());
//        mockMvc.perform(RestDocumentationRequestBuilders.post(Path,
//                                openOrderEventResponse.getEventId(),
//                                openOrderEventResponse.getQuiz().getQuizId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234")))
//                        .header("ApplyTicket",applyTicket))
//                .andExpect(status().isUnauthorized())
//                .andDo(print())
//                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
//                        resourceSnippet("선착순 퀴즈 번호 제출")));
    }

    @Test
    @DisplayName("[DOC] 선착순 이벤트 퀴즈 정답 제출")
    void makeApply() throws Exception {
        final String Path = "/event/order/{eventId}/{quizId}";
        final String DOCUMENT_NAME ="event/order/{eventId}/{quizId}";
        String applyTicket = "applyTicket";
        Mockito.when(orderEventCommandService.makeApplyTicket(any(),any(),any())).thenReturn(ResponseApplyTicketDto.applySuccess(applyTicket));
        mockMvc.perform(RestDocumentationRequestBuilders.post(Path,
                                openOrderEventResponse.getEventId(),
                                openOrderEventResponse.getQuiz().getQuizId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(RequestAnswerDto.makeWith("answer")))
                         )
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(objectMapper.writeValueAsString(ResponseApplyTicketDto.class)))
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
                        resourceSnippet("선착순 퀴즈 정답 제출")));
    }
}