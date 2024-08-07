package com.watermelon.server.lottery.parts.service;

import com.watermelon.server.lottery.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.lottery.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.lottery.parts.dto.response.ResponseRemainChanceDto;

import java.util.List;

public interface PartsService {

    /**
     * uid 에 대한 유저의 파츠 뽑기를 수행하고 결과를 반환
     * @param uid uid
     * @return 파츠 뽑기 결과
     */
    ResponsePartsDrawDto drawParts(String uid);

    void toggleParts(String uid, Long partId);
  
    ResponseRemainChanceDto getRemainChance(String uid);

    /**
     * uid 에 대한 유저의 파츠 목록을 dto 형식으로 반환
     * @param uid uid
     * @return 유저의 파츠 목록
     */
    List<ResponseMyPartsListDto> getMyParts(String uid);

    /**
     * 링크 키에 대한 유저의 파츠 목록 반환
     * @param linkKey 링크 키
     * @return 링크에 대한 유저의 파츠 목록
     */
    List<ResponseMyPartsListDto> getPartsList(String linkKey);

}
