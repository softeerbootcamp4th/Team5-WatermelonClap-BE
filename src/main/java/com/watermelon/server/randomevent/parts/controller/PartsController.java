package com.watermelon.server.randomevent.parts.controller;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.randomevent.parts.dto.response.ResponseRemainChanceDto;
import com.watermelon.server.randomevent.parts.exception.PartsDrawLimitExceededException;
import com.watermelon.server.randomevent.parts.service.PartsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/parts")
public class PartsController {

    private final PartsService partsService;

    @PostMapping
    public ResponseEntity<ResponsePartsDrawDto> drawParts(
            @Uid String uid
    ){
        try {
            return new ResponseEntity<>(partsService.drawParts(uid), HttpStatus.OK);
        }catch (PartsDrawLimitExceededException e){
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    @GetMapping("/remain")
    public ResponseEntity<ResponseRemainChanceDto> getRemainChance(
            @Uid String uid
    ){
        return new ResponseEntity<>(partsService.getRemainChance(uid), HttpStatus.OK);
    }

}
