package com.watermelon.server.event.order.service;

import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderEventQueryServiceTest {


    @InjectMocks
    private OrderEventQueryService orderEventQueryService;
    @Mock
    private OrderEventRepository orderEventRepository;

    private static List<OrderEvent> openEvents = new ArrayList<>();
    private static List<OrderEvent> unOpenEvents = new ArrayList<>();
    private static OrderEvent unOpenedOrderEvent;
    private static OrderEvent openedOrderEvent;
    @BeforeAll
    static void setUp() {
        RequestQuizDto requestQuizDto = RequestQuizDto.makeForTest();
        RequestOrderRewardDto requestOrderRewardDto = RequestOrderRewardDto.makeForTest();
        RequestOrderEventDto requestOrderEventDto = RequestOrderEventDto.makeForTestOpen10HoursLater(requestQuizDto,requestOrderRewardDto);
        unOpenedOrderEvent = OrderEvent.makeOrderEventWithOutImage(requestOrderEventDto);
        openEvents.add(unOpenedOrderEvent);
        OrderEvent openedOrderEvent = OrderEvent.makeOrderEventWithOutImage(requestOrderEventDto);
        openedOrderEvent.openEvent();
        unOpenEvents.add(openedOrderEvent);
        Assertions.assertThat(openEvents.size()).isEqualTo(1);
    }

    @DisplayName("오픈 이벤트 quiz null인 것을 확인")
    @Test
    void getOrderEvents() {
        when(orderEventRepository.findAll()).thenReturn(openEvents);
        List<ResponseOrderEventDto> responseOrderEventDtos = orderEventQueryService.getOrderEvents();
        responseOrderEventDtos.forEach( responseOrderEventDto ->
                        Assertions.assertThat(responseOrderEventDto.getQuiz()).isNull()
                );
    }
    @DisplayName("오픈 이벤트 quiz에 정답이 없는 것을 확인")
    @Test
    void getOpenedOrderEvents() {
        when(orderEventRepository.findAll()).thenReturn(unOpenEvents);
        List<ResponseOrderEventDto> responseOrderEventDtos = orderEventQueryService.getOrderEvents();
        responseOrderEventDtos.forEach( responseOrderEventDto ->
                Assertions.assertThat(responseOrderEventDto.getQuiz().getAnswer()).isNull()
        );
    }
}