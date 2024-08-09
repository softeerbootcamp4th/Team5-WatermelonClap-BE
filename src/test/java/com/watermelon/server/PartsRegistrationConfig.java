package com.watermelon.server;

import com.watermelon.server.event.lottery.parts.domain.Parts;
import com.watermelon.server.event.lottery.parts.repository.PartsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class PartsRegistrationConfig {

    @Autowired
    private PartsRepository partsRepository;

    @PostConstruct
    public void init() {
        partsRepository.saveAll(
                Parts.createAllParts()
        );
    }

}
