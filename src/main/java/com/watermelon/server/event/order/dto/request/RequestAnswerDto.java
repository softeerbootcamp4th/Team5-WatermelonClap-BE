package com.watermelon.server.event.order.dto.request;


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

    public static RequestAnswerDto makeWith(String answer) {
        return RequestAnswerDto.builder().answer(answer).build();
    }
}
