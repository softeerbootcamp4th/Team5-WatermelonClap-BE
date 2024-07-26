package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Participant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ParticipantRepositoryTest {

    final int N = -1;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    @DisplayName("추첨 순위가 n인 참여자를 제외한 참여자 리스트를 반환한다.")
    void findByLotteryRankNot() {

        //given
        Participant participantRankNotN = Participant.builder()
                .lotteryRank(N+1)
                .build();

        Participant participantRankN = Participant.builder()
                .lotteryRank(N)
                .build();

        participantRepository.save(participantRankNotN);
        participantRepository.save(participantRankN);

        //when
        List<Participant> participants = participantRepository.findByLotteryRankNot(N);

        //then
        assertThat(participants).hasSize(1);

    }
}