package com.watermelon.server.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.watermelon.server.Constants.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(AdminLotteryController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AdminLotteryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LotteryService lotteryService;

    @Autowired
    private ObjectMapper objectMapper;

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

        System.out.println(objectMapper.writeValueAsString(expected));

        //when & then
        this.mockMvc.perform(get(PATH_ADMIN_LOTTERY_WINNERS)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN)
                ).andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DOCUMENT_NAME_LOTTERY_WINNERS));

    }

}