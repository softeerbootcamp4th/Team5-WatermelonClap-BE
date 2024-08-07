package com.watermelon.server.lottery.parts.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.DocumentConstants;
import com.watermelon.server.event.lottery.parts.controller.PartsController;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.event.lottery.parts.dto.response.ResponseRemainChanceDto;
import com.watermelon.server.event.lottery.parts.exception.PartsDrawLimitExceededException;
import com.watermelon.server.event.lottery.parts.service.PartsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static com.watermelon.server.Constants.*;
import static com.watermelon.server.Constants.TEST_TOKEN;
import static com.watermelon.server.common.constants.PathConstants.PARTS_LINK_LIST;
import static org.mockito.Mockito.verify;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PartsController.class)
class PartsControllerTest extends ControllerTest {

    @MockBean
    private PartsService partsService;

    @Test
    @DisplayName("파츠 뽑기 결과를 반환한다.")
    void drawParts() throws Exception {

        final String PATH = "/event/parts";
        final String DOCUMENT_NAME = "event/parts/success";

        //given
        ResponsePartsDrawDto responsePartsDrawDto = ResponsePartsDrawDto.createResponsePartsDrawDtoTest();

        Mockito.when(partsService.drawParts(TEST_UID)).thenReturn(responsePartsDrawDto);

        String expectedResponseBody = objectMapper.writeValueAsString(responsePartsDrawDto);

        //when & then
        this.mockMvc.perform(post(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody))
                .andDo(document(DOCUMENT_NAME,
                        resourceSnippetAuthed("파츠 뽑기")));
    }

    @Test
    @DisplayName("파츠 뽑기 횟수가 소진되었을 경우 429 에러를 반환한다.")
    void drawPartsException() throws Exception {

        final String PATH = "/event/parts";
        final String DOCUMENT_NAME = "event/parts/too-many-request";

        //given
        Mockito.when(partsService.drawParts(TEST_UID)).thenThrow(new PartsDrawLimitExceededException());

        //when & then
        this.mockMvc.perform(post(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isTooManyRequests())
                .andDo(document(DOCUMENT_NAME,
                        resourceSnippetAuthed("파츠 뽑기")));

    }

    @Test
    @DisplayName("파츠 상태 변경에 성공")
    void toggleParts() throws Exception {

        final String PATH = "/event/parts/{parts_id}";
        final String DOCUMENT_NAME = "event/parts/equip";

        //when & then
        this.mockMvc.perform(patch(PATH, TEST_PARTS_ID)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isOk())
                .andDo(document(DOCUMENT_NAME,
                        resourceSnippetAuthed("자신의 파츠 상태 변경")));

        verify(partsService).toggleParts(TEST_UID, TEST_PARTS_ID);

    }

    @Test
    @DisplayName("남은 파츠 뽑기 횟수를 반환한다.")
    void getRemainChance() throws Exception {

        final String PATH = "/event/parts/remain";
        final String DOCUMENT_NAME = "event/parts/remain";

        //given
        final ResponseRemainChanceDto responseRemainChanceDto = ResponseRemainChanceDto.createTestDto();

        Mockito.when(partsService.getRemainChance(TEST_UID)).thenReturn(responseRemainChanceDto);

        String expectedResponseBody = objectMapper.writeValueAsString(responseRemainChanceDto);

        //when & then
        this.mockMvc.perform(get(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBody))
                .andDo(document(DOCUMENT_NAME,
                        resourceSnippetAuthed("자신의 남은 파츠 뽑기 횟수 조회")));

    }

    @Test
    @DisplayName("자신의 파츠 목록을 반환한다.")
    void getMyPartsList() throws Exception {

        final String PATH = "/event/parts";
        final String DOCUMENT_NAME = "event/parts/get";

        //given
        List<ResponseMyPartsListDto> responseMyPartsListDtos = ResponseMyPartsListDto.createTestDtoList();

        Mockito.when(partsService.getMyParts(TEST_UID)).thenReturn(
                responseMyPartsListDtos
        );

        String expected = objectMapper.writeValueAsString(responseMyPartsListDtos);

        //when & then
        this.mockMvc.perform(get(PATH)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(expected))
                .andDo(document(DOCUMENT_NAME,
                        resourceSnippetAuthed("자신의 파츠 목록 조회")));

    }

    @Test
    @DisplayName("링크 키의 주인에 대한 파츠 목록을 반환한다.")
    void getLinkPartsList() throws Exception {

        //given
        List<ResponseMyPartsListDto> responseMyPartsListDtos = ResponseMyPartsListDto.createTestDtoList();

        Mockito.when(partsService.getPartsList(TEST_URI)).thenReturn(responseMyPartsListDtos);

        //when & then
        this.mockMvc.perform(get(PARTS_LINK_LIST, TEST_URI)
                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + HEADER_VALUE_SPACE + TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(responseMyPartsListDtos)
                ))
                .andDo(document(DocumentConstants.PARTS_LINK_LIST,
                        resourceSnippetAuthed("링크 키의 주인에 대한 파츠 목록 조회")));

    }

}