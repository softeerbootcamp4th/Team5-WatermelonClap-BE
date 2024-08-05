package com.watermelon.server.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * firebase 초기 설정 및 빈 등록
 */
@Slf4j
@Configuration
public class FirebaseInitializer {

    /**
     * firebase 초기 설정
     * @return
     * @throws IOException
     */
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        log.info("Initializing Firebase.");
        //TODO 환경변수로 변경 필요
        FileInputStream serviceAccount = new FileInputStream("./src/main/resources/firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("heroku-sample.appspot.com")
                .build();

        try {
            return FirebaseApp.initializeApp(options);
        }catch (IllegalStateException e){
            //이미 초기화되었다면 IllegalStateException 발생, 0번째 앱 반환
            return FirebaseApp.getApps().get(0);
        }
    }

    /**
     * firebase 인증 관련 빈 등록
     * @return
     * @throws IOException
     */
    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp());
        return firebaseAuth;
    }

}
