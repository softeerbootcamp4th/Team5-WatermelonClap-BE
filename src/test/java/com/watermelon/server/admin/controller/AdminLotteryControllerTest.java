package com.watermelon.server.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.annotations.ControllerTestAnno;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.watermelon.server.Constants.*;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminLotteryController.class)
class AdminLotteryControllerTest extends ControllerTest {

    @MockBean
    private LotteryService lotteryService;

    @Test
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
                        .param(PARAM_PAGE, String.valueOf(TEST_PAGE_NUMBER))
                        .param(PARAM_SIZE, String.valueOf(TEST_PAGE_SIZE))
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_ADMIN_APPLIER));

    }

    @Test
    @DisplayName("추첨 당첨자 명단을 반환한다.")
    void getLotteryWinners() throws Exception {

        //given
        List<ResponseAdminLotteryWinnerDto> expected = List.of(
                ResponseAdminLotteryWinnerDto.createTestDto(),
                ResponseAdminLotteryWinnerDto.createTestDto()
        );

        Mockito.when(lotteryService.getAdminLotteryWinners())
                .thenReturn(expected);

        //when & then
        this.mockMvc.perform(get(PATH_ADMIN_LOTTERY_WINNERS)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_LOTTERY_WINNERS));

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
                .andDo(document(DOCUMENT_NAME_PARTS_WINNERS));

    }

    @Test
    @DisplayName("추첨 당첨자를 확인처리 한다.")
    void lotteryWinnerCheckDone() throws Exception {

        //when & then
        this.mockMvc.perform(post(PATH_ADMIN_LOTTERY_WINNER_CHECK, TEST_UID)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_ADMIN_LOTTERY_WINNER_CHECK));

        verify(lotteryService).lotteryWinnerCheck(TEST_UID);

    }

    @Test
    @DisplayName("파츠 추첨 당첨자를 확인처리 한다.")
    void partsWinnerCheckDone() throws Exception {

        //when & then
        this.mockMvc.perform(post(PATH_ADMIN_PARTS_WINNER_CHECK, TEST_UID)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_ADMIN_PARTS_WINNER_CHECK));

        verify(lotteryService).partsWinnerCheck(TEST_UID);

    }

    @Test
    @DisplayName("추첨 이벤트 응모자에 대해 추첨을 진행한다.")
    void lottery() throws Exception {

        this.mockMvc.perform(post(PATH_LOTTERY)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_LOTTERY));

        verify(lotteryService).lottery();

    }

    @Test
    @DisplayName("파츠 이벤트 응모자에 대해 추첨을 진행한다.")
    void partsLottery() throws Exception {

        this.mockMvc.perform(post(PATH_PARTS_LOTTERY)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME_PARTS_LOTTERY));

        verify(lotteryService).partsLottery();

    }

}