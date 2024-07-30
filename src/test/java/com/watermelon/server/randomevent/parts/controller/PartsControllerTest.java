package com.watermelon.server.randomevent.parts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.randomevent.auth.resolver.UidArgumentResolver;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.randomevent.parts.exception.PartsDrawLimitExceededException;
import com.watermelon.server.randomevent.parts.service.PartsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.watermelon.server.Constants.TEST_UID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartsController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PartsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartsService partsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("파츠 뽑기 결과를 반환한다.")
    void drawParts() throws Exception {

        final String PATH = "/event/parts";
        final String DOCUMENT_NAME = "event/parts";

        //given
        ResponsePartsDrawDto responsePartsDrawDto = ResponsePartsDrawDto.createResponsePartsDrawDtoTest();

        Mockito.when(partsService.drawParts(TEST_UID)).thenReturn(responsePartsDrawDto);

        String expectedResponseBody = objectMapper.writeValueAsString(responsePartsDrawDto);

        //when & then
        this.mockMvc.perform(post(PATH)
                        .header(UidArgumentResolver.HEADER_UID, TEST_UID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody))
                .andDo(document(DOCUMENT_NAME));
    }

    @Test
    @DisplayName("파츠 뽑기 횟수가 소진되었을 경우 429 에러를 반환한다.")
    void drawPartsException() throws Exception {

        final String PATH = "/event/parts";
        final String DOCUMENT_NAME = "event/parts";

        //given
        Mockito.when(partsService.drawParts(TEST_UID)).thenThrow(new PartsDrawLimitExceededException());

        //when & then
        this.mockMvc.perform(post(PATH)
                        .header(UidArgumentResolver.HEADER_UID, TEST_UID))
                .andExpect(status().isTooManyRequests())
                .andDo(document(DOCUMENT_NAME));

    }
}