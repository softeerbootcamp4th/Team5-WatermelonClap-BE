package com.watermelon.server.token;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtPayload {

    private String quizId;

    public static JwtPayload from(String quizId) {
        return JwtPayload.builder().quizId(quizId).build();
    }
}
