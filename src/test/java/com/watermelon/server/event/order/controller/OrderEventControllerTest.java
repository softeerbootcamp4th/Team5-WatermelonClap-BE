package com.watermelon.server.event.order.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.service.OrderEventCommandService;
import com.watermelon.server.event.order.service.OrderEventQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    private ResponseOrderEventDto openResponse;
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

        openResponse = ResponseOrderEventDto.forUser(openOrderEvent);
        soonOpenResponse = ResponseOrderEventDto.forUser(soonOpenOrderEvent);
        unOpenResponse = ResponseOrderEventDto.forUser(unOpenOrderEvent);

        responseOrderEventDtos.add(openResponse);
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
                        resourceSnippet("선착순 목록 조회")));
    }
    @Test
    @DisplayName("[DOC] 특정 선착순 이벤트를 가져온다")
    void getOrderEvent() throws Exception {
        final String PATH = "/event/order/{eventId}";
        final String DOCUMENT_NAME ="event/order/{eventId}";
        System.out.println(openResponse);
        Mockito.when(orderEventQueryService.getOrderEvent(openResponse.getEventId())).thenReturn(openResponse);

        mockMvc.perform(RestDocumentationRequestBuilders.get(PATH,openResponse.getEventId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(openResponse)))
                .andDo(print())
                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
                        resourceSnippet("선착순 목록 조회")));
    }

    @Test
    void makeApplyTicket() {
    }

    @Test
    void makeApply() {
    }
}