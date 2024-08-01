package com.watermelon.server.randomevent.controller;

import com.watermelon.server.randomevent.auth.annotations.Uid;
import com.watermelon.server.randomevent.dto.request.RequestExpectationDto;
import com.watermelon.server.randomevent.error.ExpectationAlreadyExistError;
import com.watermelon.server.randomevent.service.ExpectationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExpectationController {

    private final ExpectationService expectationService;
    @PostMapping(path = "/expectations")
    public ResponseEntity<Void> makeExpectation(
            @RequestBody RequestExpectationDto requestExpectationDto,
            @Uid String uid
    ) throws ExpectationAlreadyExistError {
        expectationService.makeExpectation(uid,requestExpectationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
