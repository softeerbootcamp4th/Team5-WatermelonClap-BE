package com.watermelon.server.event.order.dto.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestQuizDto {

    private String answer;

    public static RequestQuizDto makeForTest(){
        return RequestQuizDto.builder()
                .answer("testAnswer")
                .build();
    }

}
