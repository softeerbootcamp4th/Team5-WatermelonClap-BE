package com.watermelon.server.fifoevent.service;

import com.watermelon.server.ServerApplication;
import com.watermelon.server.fifoevent.domain.OrderEvent;
import com.watermelon.server.fifoevent.domain.OrderEventStatus;
import com.watermelon.server.fifoevent.dto.request.RequestOrderEventDto;
import com.watermelon.server.fifoevent.dto.request.RequestOrderRewardDto;
import com.watermelon.server.fifoevent.dto.request.RequestQuizDto;
import com.watermelon.server.fifoevent.repository.OrderEventRepository;
import com.watermelon.server.fifoevent.repository.QuizRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application-local-db.yml")
@SpringBootTest(classes = ServerApplication.class)
class OrderEventServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OrderEventServiceTest.class);
    @Autowired
    private OrderEventService orderEventService;
    @Autowired
    private OrderEventRepository orderEventRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private EntityManager em;
    @BeforeEach
    @DisplayName("선착순 이벤트 생성")
    public void makeOrderEvents(){
        RequestQuizDto requestQuizDto =RequestQuizDto.builder()
                .description("testDescription")
                .answer("testAnswer")
                .imgSrc("testImg")
                .title("testTitle")
                .build();
        RequestOrderRewardDto requestOrderRewardDto = RequestOrderRewardDto.builder()
                .name("testName")
                .imgSrc("testImg")
                .build();
        RequestOrderEventDto requestOrderEventDto = RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startTime(LocalDateTime.now().plusHours(10))
                .endTime(LocalDateTime.now().plusHours(20))
                .maxWinnerCount(100)
                .build();
        orderEventService.makeEvent(requestOrderEventDto);
    }

    @Test
    @DisplayName("상태변경")
    public void changeStatus(){
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        OrderEvent orderEvent = orderEvents.get(0);
        orderEvent.setOrderEventStatus(OrderEventStatus.CLOSED);
        Assertions.assertThat(orderEvent.getOrderEventStatus()).isEqualTo(OrderEventStatus.CLOSED);
    }

    @Test
    @DisplayName("선착순 이벤트 1초단위 상태변경 (UPCOMING->OPEN)")
    public void orderStatusChangeByTime() throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        RequestQuizDto requestQuizDto =RequestQuizDto.builder()
                .description("testDescription")
                .answer("testAnswer")
                .imgSrc("testImg")
                .title("testTitle")
                .build();
        RequestOrderRewardDto requestOrderRewardDto = RequestOrderRewardDto.builder()
                .name("testName")
                .imgSrc("testImg")
                .build();
        RequestOrderEventDto requestOrderEventDto = RequestOrderEventDto.builder()
                .requestOrderRewardDto(requestOrderRewardDto)
                .requestQuizDto(requestQuizDto)
                .startTime(now.plusSeconds(1))
                .endTime(now.plusSeconds(3))
                .maxWinnerCount(100)
                .build();
        OrderEvent orderEvent = orderEventService.makeEvent(requestOrderEventDto);
        Assertions.assertThat(orderEvent.getOrderEventStatus()).isEqualTo(OrderEventStatus.UPCOMING);
        Thread.sleep(2000L);
        orderEvent = orderEventRepository.findById(orderEvent.getId()).get();
        Assertions.assertThat(orderEvent.getOrderEventStatus()).isEqualTo(OrderEventStatus.OPEN);
        Thread.sleep(2000L);
        orderEvent = orderEventRepository.findById(orderEvent.getId()).get();
        Assertions.assertThat(orderEvent.getOrderEventStatus()).isEqualTo(OrderEventStatus.END);
    }


    @Test
    @DisplayName(" 상태 변경 확인(UPCOMING->OPEN)")
    public void checkStatusChanged(){
        List<OrderEvent> orderEvents = orderEventRepository.findAll();
        Optional<OrderEvent> orderEvent = orderEvents
                .stream()
                .filter(o -> o.getOrderEventStatus().equals(OrderEventStatus.OPEN))
                .findAny();
        Assertions.assertThat(orderEvent.isPresent()).isTrue();

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