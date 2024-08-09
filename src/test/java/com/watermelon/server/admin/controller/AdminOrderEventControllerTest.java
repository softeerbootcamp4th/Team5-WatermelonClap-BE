package com.watermelon.server.admin.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.watermelon.server.ControllerTest;
import com.watermelon.server.admin.exception.S3ImageFormatException;
import com.watermelon.server.admin.service.AdminOrderEventService;
import com.watermelon.server.event.order.controller.OrderEventController;
import com.watermelon.server.event.order.domain.OrderEvent;
import com.watermelon.server.event.order.domain.OrderEventStatus;
import com.watermelon.server.event.order.dto.request.RequestOrderEventDto;
import com.watermelon.server.event.order.dto.request.RequestOrderRewardDto;
import com.watermelon.server.event.order.dto.request.RequestQuizDto;
import com.watermelon.server.event.order.dto.response.ResponseOrderEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.watermelon.server.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminOrderEventControllerTest.class)
class AdminOrderEventControllerTest extends ControllerTest {


    @MockBean
    private AdminOrderEventService adminOrderEventService;


    private ResponseOrderEventDto openOrderEventResponse;
    private ResponseOrderEventDto soonOpenResponse;
    private ResponseOrderEventDto unOpenResponse;
    private OrderEvent soonOpenOrderEvent;
    private OrderEvent openOrderEvent;
    private OrderEvent unOpenOrderEvent;
    private List<OrderEvent> orderEvents = new ArrayList<>();
    private List<ResponseOrderEventDto> responseOrderEventDtos = new ArrayList<>();
    @BeforeEach
    void setUp(){
        openOrderEvent = makeOrderEventIdWithDoc(1L);
        soonOpenOrderEvent = makeOrderEventIdWithDoc(2L);
        unOpenOrderEvent = makeOrderEventIdWithDoc(3L);
        openOrderEvent.setOrderEventStatus(OrderEventStatus.OPEN);

        orderEvents.add(openOrderEvent);
        orderEvents.add(soonOpenOrderEvent);
        orderEvents.add(unOpenOrderEvent);

        openOrderEventResponse = ResponseOrderEventDto.forAdmin(openOrderEvent);
        soonOpenResponse = ResponseOrderEventDto.forAdmin(soonOpenOrderEvent);
        unOpenResponse = ResponseOrderEventDto.forAdmin(unOpenOrderEvent);

        responseOrderEventDtos.add(openOrderEventResponse);
        responseOrderEventDtos.add(soonOpenResponse);
        responseOrderEventDtos.add(unOpenResponse);

    }
    private static OrderEvent makeOrderEventIdWithDoc(Long id) {
        return OrderEvent.makeOrderEventWithInputIdForDocumentation(
                RequestOrderEventDto.makeForTestOpened(
                        RequestQuizDto.makeForTest(),
                        RequestOrderRewardDto.makeForTest()
                ), id
        );
    }

//    @Test
//    @DisplayName("[DOC] 어드민 선착순 이벤트 목록을 가져온다")
//    void getOrderEvents() throws Exception {
//        final String PATH = "/admin/event/order";
//        final String DOCUMENT_NAME ="admin/event/order";
//        Mockito.when(adminOrderEventService.getOrderEventsForAdmin()).thenReturn(responseOrderEventDtos);
//
//        mockMvc.perform(RestDocumentationRequestBuilders.get(PATH)
//                        .header(HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER + " " + TEST_TOKEN))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseOrderEventDtos)))
//                .andDo(print())
//                .andDo(MockMvcRestDocumentationWrapper.document(DOCUMENT_NAME,
//                        resourceSnippet("선착순 이벤트 목록 조회")));
//    }


//    @Test
//    @DisplayName("[DOC] 선착순 이벤트를 생성한다")
//    void makeOrderEvent() throws Exception {
//        final String PATH = "/admin/event/order";
//        final String DOCUMENT_NAME ="admin/event/order";
//        Mockito.when(adminOrderEventService.makeOrderEvent(any(),any(),any())).thenReturn(openOrderEventResponse);
//
//        MockMultipartFile rewardImage = new MockMultipartFile("rewardImage", "tooth.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
//        MockMultipartFile quizImage  = new MockMultipartFile("quizImage", "tooth.png", "multipart/form-data", "uploadFile".getBytes(StandardCharsets.UTF_8));
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders
//                                .multipart(HttpMethod.POST,PATH)
//                                .file(rewardImage)
//                                .file(quizImage)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.MULTIPART_FORM_DATA)
//                )
//                .andExpect(status().isOk())
//                .andDo(print());
//
//    }


    //    RestDocumentationRequestBuilders.post(PATH)
//            .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(
//            RequestOrderEventDto.makeForTestOpened(
//            RequestQuizDto.makeForTest(),RequestOrderRewardDto.makeForTest()
//                                )
//                                        ))
    @Test
    void testGetOrderEventForAdmin() {
    }

    @Test
    void getOrderEventWinnersForAdmin() {
    }

    @Test
    void handlePhoneNumberNotExistException() {
    }
}