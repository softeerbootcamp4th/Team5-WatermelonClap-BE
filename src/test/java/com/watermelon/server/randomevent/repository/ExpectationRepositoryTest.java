package com.watermelon.server.randomevent.repository;

import com.watermelon.server.randomevent.domain.Expectation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class ExpectationRepositoryTest {

    @Autowired
    private ExpectationRepository expectationRepository;

    int approvedOrderCount =40;
    int unApprovedOrderCount =40;

    @BeforeEach
    @DisplayName("허용, 미허용 기대평 각 40개씩 저장")
    void setUp() {

        for(int i = 0; i< approvedOrderCount; i++){
            Expectation expectation = new Expectation();
            expectationRepository.save(expectation);
        }
        for(int i=0;i<unApprovedOrderCount;i++){
            Expectation expectation = new Expectation();
            expectation.toggleApproved();
            expectationRepository.save(expectation);
        }
        Assertions.assertThat(expectationRepository.findAll().size()).isEqualTo(approvedOrderCount +unApprovedOrderCount);
    }

    @Test
    @DisplayName("모두 Approved된 것인지 확인")
    void allExpectationIsApproved() {
        expectationRepository.findTop30ByIsApprovedTrueOrderByCreatedAtDesc().forEach(
                expectation -> {
                }
        );
    }
    //30개
    @Test
    @DisplayName("30개만 가져오는 것 확인")
    void expectationSize30() {
        Assertions.assertThat(expectationRepository.findTop30ByIsApprovedTrueOrderByCreatedAtDesc().size())
                .isEqualTo(30);

    }
}