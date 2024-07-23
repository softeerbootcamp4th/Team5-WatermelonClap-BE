package com.watermelon.server.orderevent.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RequestQuizDto {

    private String description;
    private String answer;
    private String imgSrc;
    private String title;
}
