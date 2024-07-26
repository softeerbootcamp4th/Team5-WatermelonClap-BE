package com.watermelon.server.event.order.result.service;

import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import com.watermelon.server.event.order.result.domain.OrderResult;
import com.watermelon.server.event.order.result.repository.OrderResultRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderResultCommandServiceTest {

    @Mock
    private OrderResultRepository orderResultRepository;

    @InjectMocks
    private OrderResultCommandService orderResultCommandService;

    private String applyToken = "applyToken";

    @Test
    @DisplayName("선착순 응모 결과 생성 ")
    public void makeOrderResult() {
        when(orderResultRepository.save(any())).thenReturn(OrderResult.makeOrderEventApply(applyToken));
        OrderResult orderResult = orderResultCommandService.makeOrderEventApply(applyToken);

        Assertions.assertThat(orderResult.getApplyToken()).isEqualTo(applyToken);
    }

}