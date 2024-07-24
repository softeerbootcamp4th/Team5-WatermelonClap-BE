package com.watermelon.server.token;

import com.watermelon.server.YamlLoadFactory;
import com.watermelon.server.error.ApplyTicketWrongException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@PropertySource(value = "classpath:application-local-token.yml",factory = YamlLoadFactory.class)
@Component
public class ApplyTokenProvider {
    private final SecretKey TOKEN_SECRET;
    @Value("${apply.token.issuer}")
    private String tokenIssuer;
    public ApplyTokenProvider( @Value("${apply.token.secret}") String tokenSecret) {
        this.TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
    }
    public String createTokenByQuizId(JwtPayload payload){
        return Jwts.builder()
                .claim("quizId",payload.getQuizId())
                .issuer(tokenIssuer)
                .signWith(TOKEN_SECRET,Jwts.SIG.HS256)
                .compact();
    }

    public JwtPayload verifyToken(String jwtToken) throws ApplyTicketWrongException {
        try{
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(TOKEN_SECRET).build()
                    .parseClaimsJws(jwtToken);
            return JwtPayload.builder()
                    .quizId(claimsJws.getPayload().get("quizId",String.class))
                    .build();
        }
        catch (SignatureException e){
            throw new ApplyTicketWrongException();
        }
    }
}
