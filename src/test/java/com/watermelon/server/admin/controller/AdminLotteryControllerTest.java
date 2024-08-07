package com.watermelon.server.admin.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.event.lottery.service.LotteryService;
import com.watermelon.server.event.lottery.service.LotteryWinnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.watermelon.server.Constants.*;
import static org.mockito.Mockito.verify;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminLotteryController.class)
class AdminLotteryControllerTest extends ControllerTest {

    @MockBean
    private LotteryService lotteryService;

    @MockBean
    private LotteryWinnerService lotteryWinnerService;

    @Test
    @DisplayName("응모자 명단을 반환한다.")
    void getLotteryAppliers() throws Exception {

        //given
        Pageable pageable = PageRequest.of(TEST_PAGE_NUMBER, TEST_PAGE_SIZE);
        Page<ResponseLotteryApplierDto> expected = new PageImpl<>(List.of(
                ResponseLotteryApplierDto.createTestDto(),
                ResponseLotteryApplierDto.createTestDto()
        ), pageable, TEST_PAGE_SIZE);

        Mockito.when(lotteryService.getApplierInfoPage(pageable))
                .thenReturn(expected);

        //when & then
        this.mockMvc.perform(get(PATH_ADMIN_APPLIER)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                        .param(PARAM_PAGE, String.valueOf(TEST_PAGE_NUMBER))
                        .param(PARAM_SIZE, String.valueOf(TEST_PAGE_SIZE))
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_ADMIN_APPLIER,
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("응모자 명단 조회")
                                        .requestHeaders(
                                                headerWithName(HEADER_NAME_AUTHORIZATION).description("Bearer token for authentication"))
                                        .queryParameters(
                                                parameterWithName(PARAM_PAGE).description("페이지"),
                                                parameterWithName(PARAM_SIZE).description("페이지 크기")
                                        )
                                        .build()
                        )
                ));

    }

    @Test
    @DisplayName("추첨 당첨자 명단을 반환한다.")
    void getLotteryWinners() throws Exception {

        //given
        List<ResponseAdminLotteryWinnerDto> expected = List.of(
                ResponseAdminLotteryWinnerDto.createTestDto(),
                ResponseAdminLotteryWinnerDto.createTestDto()
        );

        Mockito.when(lotteryWinnerService.getAdminLotteryWinners())
                .thenReturn(expected);

        //when & then
        this.mockMvc.perform(get(PATH_ADMIN_LOTTERY_WINNERS)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_LOTTERY_WINNERS, resourceSnippetAuthed("추첨 당첨자 명단 조회")));

    }

    @Test
    @DisplayName("파츠 당첨자 명단을 반환한다.")
    void getPartsWinners() throws Exception {

        //given
        List<ResponseAdminPartsWinnerDto> expected = List.of(
                ResponseAdminPartsWinnerDto.createTestDto(),
                ResponseAdminPartsWinnerDto.createTestDto()
        );

        Mockito.when(lotteryService.getAdminPartsWinners())
                .thenReturn(expected);

        //when & then
        this.mockMvc.perform(get(PATH_ADMIN_PARTS_WINNERS)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_PARTS_WINNERS, resourceSnippetAuthed("파츠 당첨자 명단 조회")));

    }

    @Test
    @DisplayName("추첨 당첨자를 확인처리 한다.")
    void lotteryWinnerCheckDone() throws Exception {

        //when & then
        this.mockMvc.perform(post(PATH_ADMIN_LOTTERY_WINNER_CHECK, TEST_UID)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_ADMIN_LOTTERY_WINNER_CHECK,
                        resourceSnippetAuthed("추첨 당첨자 확인처리")));

        verify(lotteryWinnerService).lotteryWinnerCheck(TEST_UID);

    }

    @Test
    @DisplayName("파츠 추첨 당첨자를 확인처리 한다.")
    void partsWinnerCheckDone() throws Exception {

        //when & then
        this.mockMvc.perform(post(PATH_ADMIN_PARTS_WINNER_CHECK, TEST_UID)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_ADMIN_PARTS_WINNER_CHECK,
                        resourceSnippetAuthed("파츠 추첨 당첨자 확인처리")));

        verify(lotteryService).partsWinnerCheck(TEST_UID);

    }

    @Test
    @DisplayName("추첨 이벤트 응모자에 대해 추첨을 진행한다.")
    void lottery() throws Exception {

        this.mockMvc.perform(post(PATH_LOTTERY)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_LOTTERY,
                        resourceSnippetAuthed("추첨 이벤트 응모자에 대해 추첨")));

        verify(lotteryService).lottery();

    }

    @Test
    @DisplayName("파츠 이벤트 응모자에 대해 추첨을 진행한다.")
    void partsLottery() throws Exception {

        this.mockMvc.perform(post(PATH_PARTS_LOTTERY)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_PARTS_LOTTERY,
                        resourceSnippetAuthed("파츠 이벤트 응모자에 대해 추첨")));

        verify(lotteryService).partsLottery();

    }

}