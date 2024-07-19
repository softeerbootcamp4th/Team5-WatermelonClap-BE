package com.watermelon.server.fifoevent.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestFiFoEventDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String question;
    private String answer;
    private int maxWinnerCount;

    @Override
    public String toString() {
        return "RequestFiFoEventDto{" +
                "startTime=" + startTime +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", maxWinnerCount=" + maxWinnerCount +
                '}';
    }
}
