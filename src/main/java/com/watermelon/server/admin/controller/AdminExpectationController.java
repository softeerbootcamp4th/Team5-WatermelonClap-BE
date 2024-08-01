package com.watermelon.server.admin.controller;


import com.watermelon.server.admin.dto.response.ResponseAdminExpectationApprovedDto;
import com.watermelon.server.randomevent.dto.response.ResponseExpectationDto;
import com.watermelon.server.randomevent.error.ExpectationNotExist;
import com.watermelon.server.randomevent.service.ExpectationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminExpectationController {
    private ExpectationService expectationService;

    @GetMapping("/admin/expectations")
    public ResponseEntity<List<ResponseExpectationDto>> getExpectationForAdmin(){
        return new ResponseEntity<>(expectationService.getExpectationsForAdmin(), HttpStatus.OK);
    }

    @PostMapping("/admin/expectations/{expectationId}/toggle")
    public ResponseEntity<ResponseAdminExpectationApprovedDto> toggleExpectation(@PathVariable Long expectationId) throws ExpectationNotExist {
        return new ResponseEntity<>(expectationService.toggleExpectation(expectationId),HttpStatus.OK);
    }

}
