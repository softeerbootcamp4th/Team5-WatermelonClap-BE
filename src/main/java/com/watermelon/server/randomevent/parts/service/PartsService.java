package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;

import java.util.List;

public interface PartsService {

    /**
     * uid 에 대한 유저의 파츠 뽑기를 수행하고 결과를 반환
     * @param uid uid
     * @return 파츠 뽑기 결과
     */
    ResponsePartsDrawDto drawParts(String uid);

    /**
     * uid 에 대한 유저의 파츠 목록을 dto 형식으로 반환
     * @param uid uid
     * @return 유저의 파츠 목록
     */
    List<ResponseMyPartsListDto> getMyParts(String uid);

}
