package com.watermelon.server.event.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestQuizDto {

    private String answer;

    public static RequestQuizDto makeForTest(){
        return RequestQuizDto.builder()
                .answer("testAnswer")
                .build();
    }

}
