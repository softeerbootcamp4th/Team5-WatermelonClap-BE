package com.watermelon.server.randomevent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.service.LotteryService;
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

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LotteryController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class LotteryControllerTest {

    private final String PATH = "/event/lotteries";
    private final String DOCUMENT_NAME = "event/lotteries";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("JSON 형식의 랭크, 이메일 목록을 반환한다.")
    void testGetOrderEventResultSuccess() throws Exception {

        //given
        List<ResponseLotteryWinnerDto> expectedResponse = List.of(
                ResponseLotteryWinnerDto.builder()
                        .email("email1@email.com")
                        .rank(-1)
                        .build(),
                ResponseLotteryWinnerDto.builder()
                        .email("email2@email.com")
                        .rank(1)
                        .build()
        );

        Mockito.when(lotteryService.getLotteryWinners())
                .thenReturn(expectedResponse);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        //then
        this.mockMvc.perform(get(PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(document(DOCUMENT_NAME));

    }


}