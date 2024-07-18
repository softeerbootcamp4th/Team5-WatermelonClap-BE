package com.watermelon.server.fifoevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RequestFiFoEventDto {
    private LocalDateTime startTime;
    private String question;
    private String answer;
    private int maxWinnerCount;
}
