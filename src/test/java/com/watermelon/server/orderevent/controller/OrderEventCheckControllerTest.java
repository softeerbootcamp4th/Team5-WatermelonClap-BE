package com.watermelon.server.orderevent.controller;

import com.watermelon.server.orderevent.service.QuizCheckService;
import com.watermelon.server.orderevent.service.OrderEventCheckService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderEventCheckController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderEventCheckControllerTest {

    private final String TICKET_HEADER = "ApplyTicket";
    private final String ANSWER_PARAM = "answer";
    private final String DOCUMENT_NAME = "event/order/result";
    private final String PATH = "/event/order/{eventId}/{quizId}";

    final String VALID_TICKET = "validTicket";

    final String CORRECT_ANSWER = "correctAnswer";
    final String WRONG_ANSWER = "wrongAnswer";

    final String WRONG_RESULT = "wrong";
    final String SUCCESS_RESULT = "success";
    final String CLOSED_RESULT = "closed";

    final Long EVENT_ID = 1L;
    final Long QUIZ_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderEventCheckService orderEventCheckService;

    @MockBean
    private QuizCheckService quizCheckService;

    @Test
    @DisplayName("정답, 검증된 티켓, 선착순 가능한 상태이면 결과로 Success 를 반환해야 한다.")
    void testGetOrderEventResultSuccess() throws Exception {

        //when
        Mockito.when(orderEventCheckService.isAble(VALID_TICKET)).thenReturn(true);
        Mockito.when(quizCheckService.isCorrect(QUIZ_ID, CORRECT_ANSWER)).thenReturn(true);

        //then
        this.mockMvc.perform(get(PATH, EVENT_ID, QUIZ_ID)
                        .header(TICKET_HEADER, VALID_TICKET)
                        .param(ANSWER_PARAM, CORRECT_ANSWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(SUCCESS_RESULT))
                .andDo(document(DOCUMENT_NAME));

    }

    @Test
    @DisplayName("오답이면 결과로 wrong 을 반환해야 한다.")
    void testGetOrderEventResultWrongTicket() throws Exception {

        //when
        Mockito.when(orderEventCheckService.isAble(VALID_TICKET)).thenReturn(true);
        Mockito.when(quizCheckService.isCorrect(QUIZ_ID, WRONG_ANSWER)).thenReturn(false);

        //then
        this.mockMvc.perform(get(PATH, EVENT_ID, QUIZ_ID)
                        .header(TICKET_HEADER, VALID_TICKET)
                        .param(ANSWER_PARAM, WRONG_ANSWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(WRONG_RESULT))
                .andDo(document(DOCUMENT_NAME));

    }

    @Test
    @DisplayName("선착순이 마감되었으면 closed 를 반환해야 한다.")
    void testGetOrderEventResultClosed() throws Exception {

        //when
        Mockito.when(orderEventCheckService.isAble(VALID_TICKET)).thenReturn(false);
        Mockito.when(quizCheckService.isCorrect(QUIZ_ID, CORRECT_ANSWER)).thenReturn(true);

        //then
        this.mockMvc.perform(get(PATH, EVENT_ID, QUIZ_ID)
                        .header(TICKET_HEADER, VALID_TICKET)
                        .param(ANSWER_PARAM, CORRECT_ANSWER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(CLOSED_RESULT))
                .andDo(document(DOCUMENT_NAME));

    }
}