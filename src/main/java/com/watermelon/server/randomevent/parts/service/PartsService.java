package com.watermelon.server.randomevent.parts.service;

import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;

public interface PartsService {

    /**
     * uid 에 대한 유저의 파츠 뽑기를 수행하고 결과를 반환
     * @param uid uid
     * @return 파츠 뽑기 결과
     */
    ResponsePartsDrawDto drawParts(String uid);

}
