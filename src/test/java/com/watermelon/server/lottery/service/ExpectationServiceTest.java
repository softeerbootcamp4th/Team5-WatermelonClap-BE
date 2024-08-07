package com.watermelon.server.lottery.service;

import com.watermelon.server.event.lottery.domain.Expectation;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.lottery.dto.request.RequestExpectationDto;
import com.watermelon.server.lottery.error.ExpectationAlreadyExistError;
import com.watermelon.server.lottery.repository.ExpectationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class ExpectationServiceTest {

    @InjectMocks
    private ExpectationService expectationService;

    @Mock
    private ExpectationRepository expectationRepository;
    @Mock
    private LotteryService lotteryService;


    private LotteryApplier applier;
    private RequestExpectationDto requestExpectationDto;

    @BeforeEach
    void setUp() {
        applier = new LotteryApplier();
        requestExpectationDto = RequestExpectationDto.makeExpectation("ex");
    }

    @Test
    @DisplayName("기대평 작성 정상")
    void makeExpectation() {
        Mockito.when(lotteryService.findLotteryApplierByUid(any())).thenReturn(applier);
        assertDoesNotThrow(()->
                expectationService.makeExpectation("uid",requestExpectationDto));
    }

    @Test
    @DisplayName("기대평 이미 작성됨")
    void expectationAlreadyExist()  {
        Mockito.when(lotteryService.findLotteryApplierByUid(any())).thenReturn(applier);
        Expectation.makeExpectation(requestExpectationDto,applier); //기대평 한 번 작성
        Assertions.assertThatCode(()-> expectationService.makeExpectation("uid",requestExpectationDto))
                .isInstanceOf(ExpectationAlreadyExistError.class);

    }

    @Test
    @DisplayName("기대평 유저용 (기대평 내용 NotNull 다른 것은 Not Null)")
    void getExpectationsForUser() {
        List<Expectation> expectations = new ArrayList<>();
        expectations.add(Expectation.makeExpectation(RequestExpectationDto.makeExpectation("ex"),applier));
        Mockito.when(expectationRepository.findTop30ByIsApprovedTrueOrderByCreatedAtDesc()).thenReturn(expectations);


        expectationService.getExpectationsForUser().forEach(
                responseExpectationDto -> {
                    Assertions.assertThat(responseExpectationDto.getExpectation()).isNotNull();
                    Assertions.assertThat(responseExpectationDto.getExpectationId()).isNull();
                }
        );
    }
}