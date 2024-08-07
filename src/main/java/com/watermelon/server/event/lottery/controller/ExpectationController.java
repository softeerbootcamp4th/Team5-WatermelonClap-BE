package com.watermelon.server.event.lottery.controller;

import com.watermelon.server.event.lottery.auth.annotations.Uid;
import com.watermelon.server.event.lottery.dto.request.RequestExpectationDto;
import com.watermelon.server.event.lottery.dto.response.ResponseExpectationDto;
import com.watermelon.server.lottery.error.ExpectationAlreadyExistError;
import com.watermelon.server.lottery.service.ExpectationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping(path ="/expectations")
    public ResponseEntity<List<ResponseExpectationDto>> getExpectationsForUser() {
        return new ResponseEntity<>(expectationService.getExpectationsForUser(),HttpStatus.OK);
    }

}
