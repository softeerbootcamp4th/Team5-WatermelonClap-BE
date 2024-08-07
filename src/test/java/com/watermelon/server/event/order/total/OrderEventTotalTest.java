package com.watermelon.server.event.order.total;

import com.watermelon.server.BaseIntegrationTest;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class OrderEventTotalTest extends BaseIntegrationTest {

    @Autowired
    private OrderEventRepository orderEventRepository;
    private OrderEvent soonOpenOrderEvent;
    private OrderEvent openOrderEvent;
    private OrderEvent unOpenOrderEvent;

    @BeforeEach
    void setUp(){
        openOrderEvent = OrderEvent.makeOrderEvent(
                RequestOrderEventDto.makeForTestOpened(
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                )
        );
        soonOpenOrderEvent = OrderEvent.makeOrderEvent(
                RequestOrderEventDto.makeForTestOpenAfter1SecondCloseAfter3Second
                                (
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                                )
                );
        unOpenOrderEvent = OrderEvent.makeOrderEvent(
                RequestOrderEventDto.makeForTestOpen10HoursLater
                        (
                                RequestQuizDto.makeForTest(),
                                RequestOrderRewardDto.makeForTest()
                        )
        );
    }
    @AfterEach
    void tearDown(){
        orderEventRepository.deleteAll();
    }

    @Test
    @DisplayName("[통합] 선착순 이벤트 오픈된 이벤트 가져오기")
    public void getOpenOrderEvent() throws Exception {
        orderEventRepository.save(openOrderEvent);
        openOrderEvent.setOrderEventStatus(OrderEventStatus.OPEN);
        mvc.perform(get("/event/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].eventId").value(openOrderEvent.getId()))
                .andExpect(jsonPath("$[0].startDate").value(openOrderEvent.getStartDate().toString()))
                .andExpect(jsonPath("$[0].endDate").value(openOrderEvent.getEndDate().toString()))
                .andExpect(jsonPath("$[0].status").value(openOrderEvent.getOrderEventStatus().toString()))
                .andExpect(jsonPath("$[0].quiz").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 이벤트 오픈 안 된 이벤트 가져오기(NULL)")
    public void getUnOpenOrderEvent() throws Exception {
        orderEventRepository.save(unOpenOrderEvent);
        mvc.perform(get("/event/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].eventId").value(unOpenOrderEvent.getId()))
                .andExpect(jsonPath("$[0].startDate").value(unOpenOrderEvent.getStartDate().toString()))
                .andExpect(jsonPath("$[0].endDate").value(unOpenOrderEvent.getEndDate().toString()))
                .andExpect(jsonPath("$[0].status").value(unOpenOrderEvent.getOrderEventStatus().toString()))
               .andExpect(jsonPath("$[0].quiz").doesNotExist())
                .andDo(print());
    }
}
