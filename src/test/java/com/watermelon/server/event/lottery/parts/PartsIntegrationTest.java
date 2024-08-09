package com.watermelon.server.event.lottery.parts;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.watermelon.server.Constants.*;
import static com.watermelon.server.common.constants.PathConstants.PARTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[통합] 파츠 통합 테스트")
public class PartsIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private LotteryApplierRepository lotteryApplierRepository;

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private LotteryApplierPartsRepository lotteryApplierPartsRepository;

    @Test
    @DisplayName("각 카테고리의 파츠를 1개 이상 뽑으면 '나만의 아반떼 N 미니어처'를 받을 수 있는 추첨권을 부여한다.")
    void partsLotteryPolicyTest() throws Exception {

        //given
        LotteryApplier lotteryApplier = saveTestLotteryApplier();
        for(PartsCategory partsCategory:PartsCategory.values()){
            Parts parts = partsRepository.save(Parts.createTestCategoryParts(partsCategory));
            lotteryApplierPartsRepository.save(
                    LotteryApplierParts.createApplierParts(true, lotteryApplier, parts)
            );
        }

        //when
        postPartsLotteryRequestTestUser();
        boolean expected = findTestLotteryApplier().isPartsApplier();

        //then
        Assertions.assertThat(expected).isTrue();

    }

    @Test
    @DisplayName("파츠 뽑기권이 없으면 429 에러를 반환한다.")
    void partsLotteryNotRemainCountExceptionTest() throws Exception {

        //given
        postPartsLotteryRequestTestUser();

        //when
        mvc.perform(post(PARTS)
                .header( HEADER_NAME_AUTHORIZATION, HEADER_VALUE_BEARER+HEADER_VALUE_SPACE+TestTokenVerifier.TEST_VALID_TOKEN)
        )
                //then
                .andExpect(status().isTooManyRequests());

    }

    private LotteryApplier saveTestLotteryApplier() {
        LotteryApplier lotteryApplier = LotteryApplier.createLotteryApplier(TestTokenVerifier.TEST_UID);
        lotteryApplierRepository.save(lotteryApplier);
        return lotteryApplier;
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
