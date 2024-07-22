package com.watermelon.server.fifoevent.controller;

import com.watermelon.server.ServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ServerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderEventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetQuiz() throws Exception {
        this.mockMvc.perform(get("/event/fifo").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("event/fifo"));
    }
}