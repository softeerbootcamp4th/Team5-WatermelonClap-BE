package com.watermelon.server.randomevent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.randomevent.auth.service.TokenVerifier;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
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

import static com.watermelon.server.Constants.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LotteryController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @MockBean
    private TokenVerifier tokenVerifier;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("추첨자 명단을 JSON 형식으로 반환한다.")
    void testGetOrderEventResultSuccess() throws Exception {

        //given
        final String PATH = "/event/lotteries";
        final String DOCUMENT_NAME = "event/lotteries";

        List<ResponseLotteryWinnerDto> expectedResponse = List.of(
                ResponseLotteryWinnerDto.from("email1@email.com", -1),
                ResponseLotteryWinnerDto.from("email2@email.com", 1)
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

    @Test
    @DisplayName("당첨자 정보를 JSON 형식으로 반환한다.")
    void testGetOrderEventResultFailure() throws Exception {

        //given
        final String PATH = "/event/lotteries/info";
        final String DOCUMENT_NAME = "event/lotteries/info";

        ResponseLotteryWinnerInfoDto expected = ResponseLotteryWinnerInfoDto.builder()
                .name(TEST_NAME)
                .address(TEST_ADDRESS)
                .phoneNumber(TEST_PHONE_NUMBER)
                .build();

        Mockito.when(lotteryService.getLotteryWinnerInfo(TEST_UID)).thenReturn(expected);
        Mockito.when(tokenVerifier.verify(TEST_TOKEN)).thenReturn(TEST_UID);

        //then
        this.mockMvc.perform(get(PATH)
                        .header("Authorization", "Bearer " + TEST_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(TEST_NAME))
                .andExpect(jsonPath("$.address").value(TEST_ADDRESS))
                .andExpect(jsonPath("$.phoneNumber").value(TEST_PHONE_NUMBER))
                .andDo(document(DOCUMENT_NAME));

    }

    @Test
    @DisplayName("당첨자 정보가 성공적으로 저장되면 201 Status 로 응답한다.")
    void testCreateLotteryWinnerInfoSuccess() throws Exception {

        final String PATH = "/event/lotteries/info";
        final String DOCUMENT_NAME = "event/lotteries/info/create";

        //given
        RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto = RequestLotteryWinnerInfoDto.builder()
                .address(TEST_ADDRESS)
                .name(TEST_NAME)
                .phoneNumber(TEST_PHONE_NUMBER)
                .build();

        String requestJson = objectMapper.writeValueAsString(requestLotteryWinnerInfoDto);

        //then
        this.mockMvc.perform(post(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(document(DOCUMENT_NAME));

    }


}