package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.domain.Expectation;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.dto.request.RequestExpectationDto;
import com.watermelon.server.randomevent.error.ExpectationAlreadyExistError;
import com.watermelon.server.randomevent.repository.ExpectationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;

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

    @BeforeEach
    void setUp() {
        applier = new LotteryApplier();
    }

    @Test
    @DisplayName("기대평 작성 정상")
    void makeExpectation() {
        Mockito.when(lotteryService.findLotteryApplierByUid(any())).thenReturn(applier);
        assertDoesNotThrow(()->
                expectationService.makeExpectation("uid",RequestExpectationDto.makeExpectation("ex")));
    }

    @Test
    @DisplayName("기대평 이미 작성됨")
    void expectationAlreadyExist()  {
        Mockito.when(lotteryService.findLotteryApplierByUid(any())).thenReturn(applier);
        RequestExpectationDto requestExpectationDto = RequestExpectationDto.makeExpectation("ex");
        Expectation.makeExpectation(requestExpectationDto,applier); //기대평 한 번 작성


        Assertions.assertThatCode(()-> expectationService.makeExpectation("uid",requestExpectationDto))
                .isInstanceOf(ExpectationAlreadyExistError.class);




    }
}