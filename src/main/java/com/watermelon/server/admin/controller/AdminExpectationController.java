package com.watermelon.server.admin.controller;


import com.watermelon.server.randomevent.dto.response.ResponseExpectationDto;
import com.watermelon.server.randomevent.service.ExpectationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminExpectationController {
    private ExpectationService expectationService;

    @GetMapping("/admin/expectations")
    ResponseEntity<List<ResponseExpectationDto>> getExpectationForAdmin(){
        return new ResponseEntity<>(expectationService.getExpectationsForAdmin(), HttpStatus.OK);
    }
}
