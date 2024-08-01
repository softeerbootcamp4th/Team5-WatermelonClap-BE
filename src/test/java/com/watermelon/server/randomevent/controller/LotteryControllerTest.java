package com.watermelon.server.randomevent.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.MockLoginInterceptorConfig;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseRewardInfoDto;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static com.watermelon.server.Constants.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LotteryController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(MockLoginInterceptorConfig.class)
class LotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("추첨자 명단을 반환한다.")
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
        this.mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(document(DOCUMENT_NAME));

    }

    @Test
    @DisplayName("당첨자 정보를 반환한다.")
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

        //then
        this.mockMvc.perform(get(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN))
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
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(document(DOCUMENT_NAME));

    }


    @Test
    @DisplayName("응모 정보가 없으면 rank : -1, applied : false 로 응답한다.")
    void testGetLotteryRankNotAppliedCase() throws Exception {

        final String PATH = "/event/lotteries/rank";
        final String DOCUMENT_NAME = "event/lotteries/rank";

        //given
        Mockito.doThrow(new NoSuchElementException()).when(lotteryService).getLotteryRank(anyString());

        //then
        this.mockMvc.perform(get(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN))
                .andExpect(jsonPath("$.rank").value(-1))
                .andExpect(jsonPath("$.applied").value(false))
                .andDo(document(DOCUMENT_NAME)
                );

    }

    @Test
    @DisplayName("응모 정보가 있으면 해당 유저의 rank, applied : true 로 응답한다.")
    void testGetLotteryRankAppliedCase() throws Exception {

        final String PATH = "/event/lotteries/rank";
        final String DOCUMENT_NAME = "event/lotteries/rank";

        //given
        Mockito.when(lotteryService.getLotteryRank(TEST_UID)).thenReturn(
                ResponseLotteryRankDto.createAppliedTest()
        );

        //then
        this.mockMvc.perform(get(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rank").value(TEST_RANK))
                .andExpect(jsonPath("$.applied").value(true))
                .andDo(document(DOCUMENT_NAME));

    }

    @Test
    @DisplayName("추첨이벤트 경품 정보를 반환한다.")
    void getRewardInfo() throws Exception {

        final String PATH = "/event/lotteries/reward/{rank}";
        final String DOCUMENT_NAME = "event/lotteries/rank";

        //when
        Mockito.when(lotteryService.getRewardInfo(TEST_RANK)).thenReturn(
                new ResponseRewardInfoDto(TEST_IMGSRC, TEST_NAME)
        );

        //then
        this.mockMvc.perform(get(PATH, TEST_RANK))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imgSrc").value(TEST_IMGSRC))
                .andExpect(jsonPath("$.name").value(TEST_NAME))
                .andDo(document(DOCUMENT_NAME));

    }
}