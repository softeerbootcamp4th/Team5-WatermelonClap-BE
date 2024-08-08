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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.UUID;


@Component
public class ApplyTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(ApplyTokenProvider.class);
    private final SecretKey TOKEN_SECRET;
    @Value("${apply.token.issuer}")
    private String tokenIssuer;
    private final String randomClaimKey ="randomClaimKey";
    private final String eventIdClaimKey = "eventId";
    public ApplyTokenProvider( @Value("${apply.token.secret}") String tokenSecret) {
        this.TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(tokenSecret));
    }
    public String createTokenByOrderEventId(JwtPayload payload){
        return Jwts.builder()
                .claim(eventIdClaimKey,payload.getEventId())
                .claim(randomClaimKey, UUID.randomUUID())
                .issuer(tokenIssuer)
                .signWith(TOKEN_SECRET,Jwts.SIG.HS256)
                .compact();
    }

    public JwtPayload verifyToken(String jwtToken,String eventId) throws ApplyTicketWrongException {
        try{
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(TOKEN_SECRET).build()
                    .parseClaimsJws(jwtToken);
            String payLoadEventId = claimsJws.getPayload().get(eventIdClaimKey,String.class);
            if(!payLoadEventId.equals(eventId)){throw new ApplyTicketWrongException();}
            return JwtPayload.builder()
                    .eventId(eventId)
                    .build();
        }
        catch (SignatureException e){
            throw new ApplyTicketWrongException();
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new ApplyTicketWrongException();
        }
    }
}
