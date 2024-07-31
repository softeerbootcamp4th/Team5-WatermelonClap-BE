package com.watermelon.server.randomevent.service;

import com.watermelon.server.admin.dto.response.ResponseAdminLotteryWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseAdminPartsWinnerDto;
import com.watermelon.server.admin.dto.response.ResponseLotteryApplierDto;
import com.watermelon.server.randomevent.domain.LotteryApplier;
import com.watermelon.server.randomevent.dto.request.RequestLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryRankDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerDto;
import com.watermelon.server.randomevent.dto.response.ResponseLotteryWinnerInfoDto;
import com.watermelon.server.randomevent.dto.response.ResponseRewardInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LotteryService {

    /**
     * 추첨이벤트 당첨자 리스트를 반환한다.
     * @return 당첨자 리스트
     */
    List<ResponseLotteryWinnerDto> getLotteryWinners();

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 반환한다.
     * @param uid uid
     * @return 당첨자 정보
     */
    ResponseLotteryWinnerInfoDto getLotteryWinnerInfo(String uid);

    /**
     * uid 를 가진 유저에 대한 당첨자 정보를 저장한다.
     * @param uid uid
     * @param requestLotteryWinnerInfoDto 당첨자 정보
     */
    void createLotteryWinnerInfo(String uid, RequestLotteryWinnerInfoDto requestLotteryWinnerInfoDto);

    /**
     * uid 를 가진 유저에 대한 추첨 이벤트 순위를 반환한다.
     * @param uid
     * @return 순위
     */
    ResponseLotteryRankDto getLotteryRank(String uid);

    /**
     * 응모한 뒤 응모자 객체를 반환. 만약 처음 응모라면, 응모 처리.
     * @param uid
     * @return 응모자
     */
    LotteryApplier applyAndGet(String uid);

    /**
     * uid 를 가진 응모자에 대한 남은 뽑기 횟수를 반환한다.
     * @param uid uid
     * @return 뽑기 횟수
     */
    int getRemainChance(String uid);

    /**
     * rank 에 대한 경품 정보를 반환한다.
     * @param rank 순위
     * @return 경품 정보
     */
    ResponseRewardInfoDto getRewardInfo(int rank);

    /**
     * 응모자 정보를 페이지로 반환
     * @param pageable 페이지 정보
     * @return 응모자 정보 페이지
     */
    Page<ResponseLotteryApplierDto> getApplierInfoPage(Pageable pageable);

    /**
     * 당첨자 명단을 반환
     * @return 당첨자 리스트
     */
    List<ResponseAdminLotteryWinnerDto> getAdminLotteryWinners();

    /**
     * 파츠 추첨 당첨자 명단을 반환
     * @return 파츠 추첨 당첨자 리스트
     */
    List<ResponseAdminPartsWinnerDto> getAdminPartsWinners();

    /**
     * uid 에 해당하는 당첨자 어드민 확인 상태를 참으로 변경
     * @param uid 당첨자의 uid
     */
    void lotteryWinnerCheck(String uid);

}
