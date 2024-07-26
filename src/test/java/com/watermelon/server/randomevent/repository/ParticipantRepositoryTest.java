package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Participant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.watermelon.server.Constants.TEST_UID;
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

    @Test
    @DisplayName("uid 가 일치하는 참여자를 반환한다.")
    void findByUid() {

        //given
        Optional<Participant> expected = Optional.of(
                Participant.builder()
                .uid(TEST_UID)
                .build()
        );

        participantRepository.save(expected.get());

        //when
        Optional<Participant> actual = participantRepository.findByUid(TEST_UID);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}