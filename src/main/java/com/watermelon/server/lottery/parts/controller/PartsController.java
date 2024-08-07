package com.watermelon.server.lottery.parts.controller;

import com.watermelon.server.lottery.auth.annotations.Uid;
import com.watermelon.server.lottery.parts.dto.response.ResponseMyPartsListDto;
import com.watermelon.server.lottery.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.lottery.parts.dto.response.ResponseRemainChanceDto;
import com.watermelon.server.lottery.parts.exception.PartsDrawLimitExceededException;
import com.watermelon.server.lottery.parts.service.PartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  

import java.util.List;

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

    @PatchMapping("/{partsId}")
    public ResponseEntity<Void> equipParts(
            @PathVariable Long partsId,
            @Uid String uid
    ){
        partsService.toggleParts(uid, partsId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/remain")
    public ResponseEntity<ResponseRemainChanceDto> getRemainChance(
            @Uid String uid
    ) {
        return new ResponseEntity<>(partsService.getRemainChance(uid), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<ResponseMyPartsListDto>> getMyPartsList(
            @Uid String uid
    ){
        return new ResponseEntity<>(partsService.getMyParts(uid), HttpStatus.OK);

    }

    @GetMapping("/link/{link_key}")
    public ResponseEntity<List<ResponseMyPartsListDto>> getLinkPartsList(
            @PathVariable String link_key
    ){
        return new ResponseEntity<>(partsService.getPartsList(link_key), HttpStatus.OK);
    }

}
