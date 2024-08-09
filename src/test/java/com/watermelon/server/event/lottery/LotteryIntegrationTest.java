package com.watermelon.server.event.lottery;

import com.watermelon.server.BaseIntegrationTest;
import com.watermelon.server.event.lottery.auth.service.TestTokenVerifier;
import com.watermelon.server.event.lottery.domain.LotteryApplier;
import com.watermelon.server.event.lottery.parts.domain.LotteryApplierParts;
import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.domain.PartsCategory;
import com.watermelon.server.event.lottery.parts.repository.LotteryApplierPartsRepository;
import com.watermelon.server.event.lottery.parts.repository.PartsRepository;
import com.watermelon.server.event.lottery.repository.LotteryApplierRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.watermelon.server.Constants.*;
import static com.watermelon.server.common.constants.PathConstants.PARTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("[통합] 추첨 통합 테스트")
public class LotteryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private LotteryApplierRepository lotteryApplierRepository;

    @Autowired
    private PartsRepository partsRepository;

    @BeforeEach
    void setUp() {
        partsRepository.saveAll(Parts.createAllParts());
    }

    @Test
    @DisplayName("내 아반떼 N 뽑기 이벤트에 최초 참여시 자동으로 응모된다.")
    void applySuccessTest() throws Exception {

        //when
        postPartsLotteryRequestTestUser();

        //then
        LotteryApplier lotteryApplier = findTestLotteryApplier();
        Assertions.assertThat(lotteryApplier.isLotteryApplier()).isTrue();
    }

    private LotteryApplier findTestLotteryApplier() {
        final String firstUserUid = TestTokenVerifier.TEST_UID;
        return lotteryApplierRepository.findByUid(firstUserUid).orElseThrow();
    }

    private void postPartsLotteryRequestTestUser() throws Exception {
        final String firstUserToken = TestTokenVerifier.TEST_VALID_TOKEN;
        mvc.perform(post(PARTS).header(
                HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+ firstUserToken
        ));
    }

}
