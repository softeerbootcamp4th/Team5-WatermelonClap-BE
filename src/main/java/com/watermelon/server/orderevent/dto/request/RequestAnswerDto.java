package com.watermelon.server.orderevent.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAnswerDto {
    private String answer;
    private Long fifoEventId;
}
