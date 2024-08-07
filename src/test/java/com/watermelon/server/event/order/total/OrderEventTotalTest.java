package com.watermelon.server.event.order.total;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.BaseIntegrationTest;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.dto.request.OrderEventWinnerRequestDto;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("[통합] 사용자 선착순 이벤트 ")
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
    @DisplayName("[통합] 선착순 이벤트 오픈된 이벤트 가져오기 - quiz = not exist")
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
    @DisplayName("[통합] 선착순 이벤트 퀴즈 - answer = null")
    public void getOpenOrderEventQuizAnswerNotExit() throws Exception {
        orderEventRepository.save(openOrderEvent);
        openOrderEvent.setOrderEventStatus(OrderEventStatus.OPEN);
        mvc.perform(get("/event/order"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quiz.answer").doesNotExist())
                .andDo(print());
    }
    @Test
    @DisplayName("[통합] 선착순 이벤트 오픈 안 된 이벤트 가져오기")
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

    @Test
    @DisplayName("[통합] 존재하는 선착순 이벤트 가져오기")
    public void getExistOpenOrderEvent() throws Exception {
        orderEventRepository.save(unOpenOrderEvent);
        mvc.perform(get("/event/order/{eventId}",unOpenOrderEvent.getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @Test
    @DisplayName("[통합] 존재하는 선착순 이벤트 가져오기")
    public void getNotExistOpenOrderEvent() throws Exception {
        orderEventRepository.save(unOpenOrderEvent);
        mvc.perform(get("/event/order/{eventId}",unOpenOrderEvent.getId()+1))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 번호 제출-  전화 번호 형식 잘못됨 (에러)")
    public void orderEventApplyPhoneNumberFormatWrong() throws Exception {
        orderEventRepository.save(openOrderEvent);
        OrderEventWinnerRequestDto emptyPhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("");
        OrderEventWinnerRequestDto notStartWith010PhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("23434343333");
        OrderEventWinnerRequestDto toLongPhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("010232323435");
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyPhoneNumberDto))
                        .header("ApplyTicket","ex"))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notStartWith010PhoneNumberDto))
                        .header("ApplyTicket","ex"))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toLongPhoneNumberDto))
                        .header("ApplyTicket","ex"))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 번호 제출 - ApplyTicket 형식 맞지 않음")
    public void orderEventApplyTicketWrong() throws Exception {
        orderEventRepository.save(openOrderEvent);
        OrderEventWinnerRequestDto emptyPhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234");
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyPhoneNumberDto))
                        .header("ApplyTicket","ex"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }



}
