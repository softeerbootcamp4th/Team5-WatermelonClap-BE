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

    @Autowired
    private LotteryApplierPartsRepository lotteryApplierPartsRepository;

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
    @DisplayName("[예정] 공유된 링크를 통해 이벤트 페이지로 이동해 가입한 사람이 있다면 링크의 유저에게 뽑기권을 1개 지급한다.")
    void test2() {

    }

    @Test
    @DisplayName("[예정] 공유한 링크를 통해 컬렉션 페이지에 방문한 사람에게는 장착된 파츠만 전달한다.")
    void test3() {

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
