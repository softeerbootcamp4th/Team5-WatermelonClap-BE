package com.watermelon.server.event.order.result.service;

import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.token.ApplyTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RSet;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderResultCommandServiceTest {


    @Mock
    private ApplyTokenProvider applyTokenProvider;
    @Mock
    private OrderResultQueryService orderResultQueryService;
    @Mock
    private RSet<OrderResult> orderResultSet;
    @InjectMocks
    private OrderResultCommandService orderResultCommandService;

    private String applyToken = "applyToken";

    @BeforeEach
    public void setUp() {
        when(applyTokenProvider.createTokenByOrderEventId(any())).thenReturn(applyToken);
    }
    @Test
    @DisplayName("선착순 응모 결과 생성(정상) ")
    public void makeOrderResult() {
        when(orderResultSet.add(any())).thenReturn(true);
        when(orderResultQueryService.isOrderApplyNotFull()).thenReturn(true);
        Assertions.assertThat(orderResultCommandService.isOrderResultFullElseMake(1L).getResult())
                .isEqualTo(ApplyTicketStatus.SUCCESS.name());
    }
    @Test
    @DisplayName("선착순 응모 결과 생성(선착순 FULL)")
    public void makeOrderResultFull() {

        when(orderResultQueryService.isOrderApplyNotFull()).thenReturn(false);
        Assertions.assertThat(orderResultCommandService.isOrderResultFullElseMake(1L).getResult())
                .isEqualTo(ApplyTicketStatus.CLOSED.name());
    }

}