package com.watermelon.server.randomevent.link.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.watermelon.server.Constants;
import com.watermelon.server.DocumentConstants;
import com.watermelon.server.MockLoginInterceptorConfig;
import com.watermelon.server.randomevent.controller.LotteryController;
import com.watermelon.server.randomevent.link.dto.MyLinkDto;
import com.watermelon.server.randomevent.link.service.LinkService;
import com.watermelon.server.randomevent.service.LotteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.watermelon.server.Constants.*;
import static com.watermelon.server.common.constants.PathConstants.MY_LINK;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LinkController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(MockLoginInterceptorConfig.class)
class LinkControllerTest {

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
}