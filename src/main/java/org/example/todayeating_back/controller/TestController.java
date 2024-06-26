package org.example.todayeating_back.controller;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/api/v1/test")
    public ResponseEntity<?> Test(){

        return ResponseEntity.ok().body(testService.test());
    }
}
