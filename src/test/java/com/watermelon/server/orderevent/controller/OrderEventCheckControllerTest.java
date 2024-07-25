package com.watermelon.server.orderevent.controller;

import com.watermelon.server.event.order.controller.OrderEventCheckController;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventResultDto;
import com.watermelon.server.event.order.service.OrderEventCheckService;
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


    @Test
    @DisplayName("success 를 반환한다.")
    void testGetOrderEventResultSuccess() throws Exception {

        //given
        Mockito.when(orderEventCheckService.getOrderEventResult(
                VALID_TICKET, EVENT_ID, QUIZ_ID, CORRECT_ANSWER
        )).thenReturn(
                ResponseOrderEventResultDto.builder()
                        .result(SUCCESS_RESULT)
                        .build()
        );

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
    @DisplayName("wrong 을 반환한다.")
    void testGetOrderEventResultWrongTicket() throws Exception {

        //given
        Mockito.when(orderEventCheckService.getOrderEventResult(
                VALID_TICKET, EVENT_ID, QUIZ_ID, WRONG_ANSWER
        )).thenReturn(
                ResponseOrderEventResultDto.builder()
                        .result(WRONG_RESULT)
                        .build()
        );

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
    @DisplayName("closed 를 반환한다.")
    void testGetOrderEventResultClosed() throws Exception {

        //given
        Mockito.when(orderEventCheckService.getOrderEventResult(
                VALID_TICKET, EVENT_ID, QUIZ_ID, CORRECT_ANSWER
        )).thenReturn(
                ResponseOrderEventResultDto.builder()
                        .result(CLOSED_RESULT)
                        .build()
        );

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