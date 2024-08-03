package com.watermelon.server.randomevent.link.service;

import com.watermelon.server.randomevent.link.dto.MyLinkDto;

public interface LinkService {

    /**
     * 추첨 이벤트 응모자에 대한 링크를 반환.
     * @param uid 응모자의 uid
     * @return 링크 정보
     */
    MyLinkDto getMyLink(String uid);

}
