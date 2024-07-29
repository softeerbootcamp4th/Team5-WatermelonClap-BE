package com.watermelon.server.randomevent.parts.controller;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import com.watermelon.server.randomevent.parts.dto.response.ResponsePartsDrawDto;
import com.watermelon.server.randomevent.parts.service.PartsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity<>(partsService.drawParts(uid), HttpStatus.OK);
    }

}
