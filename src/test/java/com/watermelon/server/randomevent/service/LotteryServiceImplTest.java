package com.watermelon.server.randomevent.service;

import com.watermelon.server.randomevent.domain.Participant;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.repository.ParticipantRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LotteryServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @InjectMocks
    private LotteryServiceImpl lotteryService;

    @Test
    @DisplayName("레포지토리에서 반환된 값을 ResponseLotteryWinnerDto 로 감싸서 반환한다.")
    void getLotteryWinners() {

        //given
        List<ResponseLotteryWinnerDto> expected = List.of(
                ResponseLotteryWinnerDto.from("email1@email.com", 1)
        );

        Mockito.when(participantRepository.findByLotteryRankNot(-1)).thenReturn(
                List.of(
                        Participant.builder()
                                .lotteryRank(1)
                                .email("email1@email.com")
                                .build()
                )
        );

        //when
        List<ResponseLotteryWinnerDto> actual = lotteryService.getLotteryWinners();

        //then
        assertThat(actual).isEqualTo(expected);

    }
}