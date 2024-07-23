package com.watermelon.server.token;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtPayload {

    private Long quizId;
}
