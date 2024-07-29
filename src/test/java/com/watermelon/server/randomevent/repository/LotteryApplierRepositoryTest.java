package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.LotteryApplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.watermelon.server.Constants.TEST_UID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LotteryApplierRepositoryTest {

    final int N = -1;

    @Autowired
    private LotteryApplierRepository lotteryApplierRepository;

    @Test
    @DisplayName("추첨 순위가 n인 참여자를 제외한 참여자 리스트를 반환한다.")
    void findByLotteryRankNot() {

        //given
        LotteryApplier lotteryApplierRankNotN = LotteryApplier.builder()
                .lotteryRank(N+1)
                .build();

        LotteryApplier lotteryApplierRankN = LotteryApplier.builder()
                .lotteryRank(N)
                .build();

        lotteryApplierRepository.save(lotteryApplierRankNotN);
        lotteryApplierRepository.save(lotteryApplierRankN);

        //when
        List<LotteryApplier> lotteryAppliers = lotteryApplierRepository.findByLotteryRankNot(N);

        //then
        assertThat(lotteryAppliers).hasSize(1);

    }

    @Test
    @DisplayName("uid 가 일치하는 참여자를 반환한다.")
    void findByUid() {

        //given
        Optional<LotteryApplier> expected = Optional.of(
                LotteryApplier.builder()
                .uid(TEST_UID)
                .build()
        );

        lotteryApplierRepository.save(expected.get());

        //when
        Optional<LotteryApplier> actual = lotteryApplierRepository.findByUid(TEST_UID);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}