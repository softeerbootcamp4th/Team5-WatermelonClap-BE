package com.watermelon.server.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.watermelon.server.randomevent.auth.exception.InvalidTokenException;
import com.watermelon.server.randomevent.auth.service.TokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * firebase 로 TokenVerifier 를 구현
 */
@Service
@RequiredArgsConstructor
public class FirebaseTokenVerifier implements TokenVerifier {

    private final FirebaseAuth firebaseAuth;

    @Override
    public String verify(String token) {

        FirebaseToken decodeToken;
        try {
            decodeToken = firebaseAuth.verifyIdToken(token);
        }catch (FirebaseAuthException e) {
            throw new InvalidTokenException();
        }

        return decodeToken.getUid();
    }

}