package com.watermelon.server.fifoevent.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@RequiredArgsConstructor
public class RequestFiFoEventDto {
    private LocalDateTime statTime;
    private String question;
    private String answer;
    private int maxWinnerCount;
}
