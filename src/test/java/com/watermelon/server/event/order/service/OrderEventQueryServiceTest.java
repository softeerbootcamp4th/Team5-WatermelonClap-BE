package com.watermelon.server.event.order.service;

import com.watermelon.server.admin.dto.response.ResponseAdminQuizDto;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import jakarta.persistence.Id;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderEventQueryServiceTest {


    @InjectMocks
    private OrderEventQueryService orderEventQueryService;
    @Mock
    private OrderEventRepository orderEventRepository;

    private static List<OrderEvent> orderEvents = new ArrayList<>();

    private static OrderEvent orderEvent;
    @BeforeAll
    static void setUp() {
        RequestQuizDto requestQuizDto = RequestQuizDto.makeForTest();
        RequestOrderRewardDto requestOrderRewardDto = RequestOrderRewardDto.makeForTest();
        RequestOrderEventDto requestOrderEventDto = RequestOrderEventDto.makeForTest(requestQuizDto,requestOrderRewardDto);
        orderEvent = OrderEvent.makeOrderEvent(requestOrderEventDto);
        orderEvents.add(orderEvent);
        Assertions.assertThat(orderEvents.size()).isEqualTo(1);
    }

    @DisplayName("Admin용 Response가 아님을 확인")
    @Test
    void getOrderEvents() {
        when(orderEventRepository.findAll()).thenReturn(orderEvents);

        List<ResponseOrderEventDto> responseOrderEventDtos = orderEventQueryService.getOrderEvents();

        responseOrderEventDtos.forEach( responseOrderEventDto ->
                        Assertions.assertThat(responseOrderEventDto).isNotInstanceOf(ResponseAdminQuizDto.class)
                );

    }
}