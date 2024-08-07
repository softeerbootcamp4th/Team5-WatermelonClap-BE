package com.watermelon.server.lottery.link.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.DocumentConstants;
import com.watermelon.server.event.lottery.link.controller.LinkController;
import com.watermelon.server.event.lottery.link.dto.MyLinkDto;
import com.watermelon.server.event.lottery.link.service.LinkService;
import com.watermelon.server.event.lottery.link.utils.LinkUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.watermelon.server.Constants.*;
import static com.watermelon.server.common.constants.PathConstants.MY_LINK;
import static com.watermelon.server.common.constants.PathConstants.SHORTED_LINK;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LinkController.class)
class LinkControllerTest extends ControllerTest {

    //TODO 추상클래스 상속 구조로 변경
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LinkService linkService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMyLink() throws Exception {

        //given
        MyLinkDto expected = MyLinkDto.createTestDto();

        Mockito.when(linkService.getMyLink(TEST_UID))
                .thenReturn(expected);

        //when & then
        this.mockMvc.perform(get(MY_LINK)
                .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expected)))
                .andDo(document(DocumentConstants.MY_LINK));

    }

    @Test
    @DisplayName("링크의 URI 가 포함된 주소로 리디렉션한다.")
    void redirect() throws Exception {

        //given
        Mockito.when(linkService.getUrl(TEST_SHORTED_URI)).thenReturn(TEST_URI);

        //when & then
        this.mockMvc.perform(get(SHORTED_LINK, TEST_SHORTED_URI))
                .andExpect(status().isFound())
                .andExpect(header().string(HEADER_NAME_LOCATION, LinkUtils.makeUrl(TEST_URI)));
    }
}