package com.watermelon.server.orderevent.dto.response;


import lombok.Getter;


@Getter
public class ResponseQuizResultFailDto extends ResponseQuizResultDto {
    private String message;

    public ResponseQuizResultFailDto(boolean isSuccess, String message) {
        super(isSuccess);
        this.message = message;
    }
}
