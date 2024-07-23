package com.watermelon.server.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;


@PropertySource("classpath:application-local-token.yml")
@Service
public class ApplyTokenProvider {

    private final SecretKey TOKEN_SECRET;
    @Value("${apply.token.issuer}")
    private String TOKEN_ISSUER;
    public ApplyTokenProvider(@Value("${apply.token.secret}") String tokenSecret) {
        this.TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
    }
    public String createToken(JwtPayload payload){
        return Jwts.builder()
                .claim("quizId",payload.getQuizId())
                .issuer(TOKEN_ISSUER)
                .signWith(TOKEN_SECRET,Jwts.SIG.HS512)
                .compact();
    }

    public JwtPayload verifyToken(String jwtToken){
        Jws<Claims> claimsJws = Jwts.parser().verifyWith(TOKEN_SECRET).build()
                .parseClaimsJws(jwtToken);
        return JwtPayload.builder()
                .quizId(claimsJws.getPayload().get("quizId",Long.class))
                .build();
    }
}
