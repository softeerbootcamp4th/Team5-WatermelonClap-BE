package com.watermelon.server.lottery.service;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.event.lottery.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.event.lottery.repository.LotteryApplierRepository;
import com.watermelon.server.event.lottery.service.LotteryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.watermelon.server.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LotteryServiceImplTest {

    @Mock
    private LotteryApplierRepository lotteryApplierRepository;

    @InjectMocks
    private LotteryServiceImpl lotteryService;

    @Test
    @DisplayName("uid 에 해당하는 참가자가 있으면, rank 와 참가여부를 반환한다.")
    void getLotteryRankPresentationCase() {

        //given
        Mockito.when(lotteryApplierRepository.findByUid(TEST_UID)).thenReturn(
                Optional.of(LotteryApplier.builder().lotteryRank(TEST_RANK).build())
        );

        //when
        ResponseLotteryRankDto responseLotteryRankDto = lotteryService.getLotteryRank(TEST_UID);

        //then
        assertThat(responseLotteryRankDto.getRank()).isEqualTo(TEST_RANK);
        assertThat(responseLotteryRankDto.isApplied()).isTrue();

    }

    @Test
    @DisplayName("uid 에 해당하는 참가자가 없으면, NoSuchElementException 을 던진다.")
    void getLotteryRankNotFoundCase() {
        //given
        Mockito.when(lotteryApplierRepository.findByUid(TEST_UID))
                .thenThrow(new NoSuchElementException());

        //when & then
        assertThatThrownBy(()->lotteryService.getLotteryRank(TEST_UID)).isInstanceOf(NoSuchElementException.class);

    }

    @Test
    @DisplayName("처음 응모라면 응모로 처리된다.")
    void applyAndGet() {

        //given
        Mockito.when(lotteryApplierRepository.findByUid(TEST_UID)).thenReturn(
                Optional.ofNullable(LotteryApplier.builder()
                        .uid(TEST_UID)
                        .isLotteryApplier(false)
                        .build())
        );

        //when
        lotteryService.applyAndGet(TEST_UID);

        ArgumentCaptor<LotteryApplier> captor = ArgumentCaptor.forClass(LotteryApplier.class);

        //then
        verify(lotteryApplierRepository).save(captor.capture());
        assertThat(captor.getValue().isLotteryApplier()).isTrue();

    }

    @Test
    @DisplayName("회원가입")
    void registration() {

        //given
        ArgumentCaptor<LotteryApplier> captor = ArgumentCaptor.forClass(LotteryApplier.class);

        //when
        lotteryService.registration(TEST_UID);

        //then
        verify(lotteryApplierRepository, times(1)).save(captor.capture());

        assertThat(captor.getValue().getUid()).isEqualTo(TEST_UID);

    }

    @Test
    @DisplayName("응모자가 이미 존재")
    void isExist() {

        //when
        lotteryService.isExist(TEST_UID);

        //then
        verify(lotteryApplierRepository).existsByUid(TEST_UID);

    }
}