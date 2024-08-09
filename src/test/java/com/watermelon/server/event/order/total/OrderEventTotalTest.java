package com.watermelon.server.event.order.total;

import com.watermelon.server.BaseIntegrationTest;
import com.watermelon.server.event.order.domain.ApplyTicketStatus;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.domain.Quiz;
import com.watermelon.server.event.order.dto.request.*;
import com.watermelon.server.event.order.repository.OrderEventRepository;
import com.watermelon.server.event.order.result.service.OrderResultQueryService;
import com.watermelon.server.event.order.service.OrderEventCheckService;
import com.watermelon.server.token.ApplyTokenProvider;
import com.watermelon.server.token.JwtPayload;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("[통합] 사용자 선착순 이벤트 ")
@Slf4j
public class OrderEventTotalTest extends BaseIntegrationTest {

    @Autowired
    private OrderEventRepository orderEventRepository;
    @Autowired
    private OrderEventCheckService orderEventCheckService;
    @Autowired
    private OrderResultQueryService orderResultQueryService;

    @Autowired
    private ApplyTokenProvider applyTokenProvider;
    private OrderEvent soonOpenOrderEvent;
    private OrderEvent openOrderEvent;
    private OrderEvent unOpenOrderEvent;


    @BeforeEach
    void setUp(){
        openOrderEvent = OrderEvent.makeOrderEventWithOutImage(
                RequestOrderEventDto.makeForTestOpened(
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                )
        );
        soonOpenOrderEvent = OrderEvent.makeOrderEventWithOutImage(
                RequestOrderEventDto.makeForTestOpenAfter1SecondCloseAfter3Second
                                (
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                                )
                );
        unOpenOrderEvent = OrderEvent.makeOrderEventWithOutImage(
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
        orderResultQueryService.getOrderResultRset().clear();
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
    @DisplayName("[통합] 선착순 퀴즈 번호 제출 - 성공")
    public void orderEventApplyTicketNotWrong() throws Exception {
        orderEventRepository.save(openOrderEvent);
        String applyTicket = applyTokenProvider.createTokenByOrderEventId(
                JwtPayload.from(String.valueOf(openOrderEvent.getId()))
        );

        OrderEventWinnerRequestDto emptyPhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234");
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyPhoneNumberDto))
                        .header("ApplyTicket",applyTicket))
                .andExpect(status().isOk())
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
    @DisplayName("[통합] 선착순 퀴즈 번호 제출 - ApplyTicket 형식 맞지 않음(다른 Claim key)")
    public void orderEventApplyTicketEventIdWrong() throws Exception {
        orderEventRepository.save(openOrderEvent);
        String applyTicket = applyTokenProvider.createTokenByOrderEventId(
                JwtPayload.from(String.valueOf(openOrderEvent.getId()+1))
        );

        OrderEventWinnerRequestDto emptyPhoneNumberDto =
                OrderEventWinnerRequestDto.makeWithPhoneNumber("01012341234");
        mvc.perform(post("/event/order/{eventId}/{quizId}/apply",openOrderEvent.getId(),1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyPhoneNumberDto))
                        .header("ApplyTicket",applyTicket))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 제출 - 성공")
    public void orderEventApply() throws Exception {
        orderEventRepository.save(openOrderEvent);
        orderEventCheckService.refreshOrderEventInProgress(openOrderEvent);
        Quiz quiz = openOrderEvent.getQuiz();
        RequestAnswerDto requestAnswerDto = RequestAnswerDto.makeWith(quiz.getAnswer());
        mvc.perform(post("/event/order/{eventId}/{quizId}",openOrderEvent.getId(),quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAnswerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(ApplyTicketStatus.SUCCESS.toString()))
                .andExpect(jsonPath("$.applyTicket").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 제출 - 실패(에러 - 현재 진행되지 않는 이벤트,퀴즈 ID)")
    public void orderEventApplyWrongEventId() throws Exception {
        orderEventRepository.save(openOrderEvent);
        orderEventCheckService.refreshOrderEventInProgress(openOrderEvent);
        Quiz quiz = openOrderEvent.getQuiz();
        RequestAnswerDto requestAnswerDto = RequestAnswerDto.makeWith(quiz.getAnswer());
        mvc.perform(post("/event/order/{eventId}/{quizId}",openOrderEvent.getId()+1L,quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAnswerDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 제출 - 실패(에러 - 기간이 틀림)")
    public void orderEventApplyWrongDuration() throws Exception {
        orderEventRepository.save(unOpenOrderEvent);
        orderEventCheckService.refreshOrderEventInProgress(unOpenOrderEvent);
        Quiz quiz = unOpenOrderEvent.getQuiz();
        RequestAnswerDto requestAnswerDto = RequestAnswerDto.makeWith(quiz.getAnswer());
        mvc.perform(post("/event/order/{eventId}/{quizId}",unOpenOrderEvent.getId(),quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAnswerDto)))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 제출 - 실패(정답이 틀림)")
    public void orderEventApplyWrongAnswer() throws Exception {
        orderEventRepository.save(openOrderEvent);
        orderEventCheckService.refreshOrderEventInProgress(openOrderEvent);
        Quiz quiz = openOrderEvent.getQuiz();
        RequestAnswerDto requestAnswerDto = RequestAnswerDto.makeWith(quiz.getAnswer()+"/wrong");
        mvc.perform(post("/event/order/{eventId}/{quizId}",openOrderEvent.getId(),quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAnswerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(ApplyTicketStatus.WRONG.toString()))
                .andExpect(jsonPath("$.applyTicket").doesNotExist())
                .andDo(print());
    }

    @Test
    @DisplayName("[통합] 선착순 퀴즈 제출 - 실패(선착순 마감)")
    public void orderEventApplyClosed() throws Exception {
        orderEventRepository.save(openOrderEvent);
        orderEventCheckService.refreshOrderEventInProgress(openOrderEvent);

        Quiz quiz = openOrderEvent.getQuiz();
        RequestAnswerDto requestAnswerDto = RequestAnswerDto.makeWith(quiz.getAnswer());


        /**
         * 선착순 최대 인원 수만큼 응모 추가
         */
        for(int i=0;i<orderResultQueryService.getAvailableTicket();i++){
            mvc.perform(post("/event/order/{eventId}/{quizId}",openOrderEvent.getId(),quiz.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(requestAnswerDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(ApplyTicketStatus.SUCCESS.toString()))
                    .andExpect(jsonPath("$.applyTicket").exists());
        }


        Assertions.assertThat(orderResultQueryService.getOrderResultRset().size()).isEqualTo(100);
        mvc.perform(post("/event/order/{eventId}/{quizId}",openOrderEvent.getId(),quiz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestAnswerDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(ApplyTicketStatus.CLOSED.toString()))
                .andExpect(jsonPath("$.applyTicket").doesNotExist())
                .andDo(print());
    }

}
