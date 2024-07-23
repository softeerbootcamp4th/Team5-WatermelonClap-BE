package com.watermelon.server.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.watermelon.server.randomevent.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FirebaseTokenVerifierTest {

    private final FirebaseToken firebaseToken;
    private final FirebaseAuth firebaseAuth;
    private final FirebaseTokenVerifier verifier;
    private final String VALID_TOKEN = "valid-token";
    private final String INVALID_TOKEN = "invalid-token";
    private final String UID = "test-uid";

    private FirebaseTokenVerifierTest () {
        this.firebaseAuth = Mockito.mock(FirebaseAuth.class);
        this.firebaseToken = Mockito.mock(FirebaseToken.class);
        this.verifier = new FirebaseTokenVerifier(firebaseAuth);
    }

    @Test
    @DisplayName("검증된 토큰일 경우 해당하는 uid를 반환해야 한다.")
    void verify() throws FirebaseAuthException {

        //given
        Mockito.when(firebaseToken.getUid()).thenReturn(UID);
        Mockito.when(firebaseAuth.verifyIdToken(VALID_TOKEN)).thenReturn(firebaseToken);

        //when
        String actual = verifier.verify(VALID_TOKEN);

        //then
        assertThat(actual).isEqualTo(UID);
    }

    @Test
    @DisplayName("검증되지 않은 토큰인 경우에는 InvalidTokenException 을 던진다.")
    void verifyThrowException() throws FirebaseAuthException {

        //given
        Mockito.when(firebaseAuth.verifyIdToken(INVALID_TOKEN)).thenThrow(new InvalidTokenException());

        //when then
        assertThrows(InvalidTokenException.class, () -> verifier.verify(INVALID_TOKEN));

    }

}