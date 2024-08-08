package com.watermelon.server.event.lottery.parts.repository;

import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.domain.PartsCategory;
import com.watermelon.server.event.lottery.repository.LotteryApplierRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LotteryApplierPartsRepositoryTest {

    @Autowired
    private LotteryApplierPartsRepository lotteryApplierPartsRepository;

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private LotteryApplierRepository lotteryApplierRepository;

    @Test
    @DisplayName("카테고리별 중복 없는 파츠 수를 반환한다.")
    void countDistinctPartsCategoryByLotteryApplier() {

        //given
        LotteryApplier lotteryApplier = Mockito.mock(LotteryApplier.class);
        lotteryApplierRepository.save(lotteryApplier);

        saveLotteryApplierAllParts(lotteryApplier);
        saveLotteryApplierAllParts(lotteryApplier);

        //when
        long actual = lotteryApplierPartsRepository.countDistinctPartsCategoryByLotteryApplier(lotteryApplier);

        //then
        Assertions.assertThat(actual).isEqualTo(PartsCategory.values().length);

    }

    private void saveLotteryApplierAllParts(LotteryApplier lotteryApplier) {
        for (PartsCategory category : PartsCategory.values()) {
            Parts parts = Parts.createTestCategoryParts(category);
            partsRepository.save(parts);
            lotteryApplierPartsRepository.save(
                    LotteryApplierParts.createApplierParts(
                            false,
                            lotteryApplier,
                            parts
                    )
            );
        }
    }

}