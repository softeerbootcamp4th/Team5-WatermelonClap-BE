package com.watermelon.server.fifoevent.service;

import com.watermelon.server.ServerApplication;
import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.fifoevent.repository.OrderEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;


//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-local-db.yml")
@SpringBootTest(classes = ServerApplication.class)
class OrderEventServiceTest {

    @Autowired
    private OrderEventService orderEventService;
    @Autowired
    private OrderEventRepository orderEventRepository;
    @Autowired
    private QuizRepository quizRepository;

    @BeforeEach
    public void makeOrderEvent(){

        RequestOrderEventDto requestOrderEventDto = RequestOrderEventDto.builder()
                .startTime(LocalDateTime.now())
                .description("test-question")
                .answer("test-answer")
                .maxWinnerCount(100)
                .build();
        orderEventService.makeEvent(requestOrderEventDto);
    }

    @Test
    public void makeQuizTest(){
        //then
        Assertions.assertThat(orderEventRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(quizRepository.findAll().size()).isEqualTo(1);
    }
    @Test
    public void deleteEventWithQuiz(){
        orderEventRepository.deleteAll();
        Assertions.assertThat(orderEventRepository.findAll().size()).isEqualTo(0);
    }

}