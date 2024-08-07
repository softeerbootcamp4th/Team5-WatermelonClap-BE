package com.watermelon.server.event.lottery.dto.request;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestExpectationDto {
    private String expectation;

    public static RequestExpectationDto makeExpectation(String expectation) {
        return RequestExpectationDto.builder()
                .expectation(expectation)
                .build();
    }
}
