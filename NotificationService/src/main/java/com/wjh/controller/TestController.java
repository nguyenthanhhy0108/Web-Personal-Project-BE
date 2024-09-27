package com.wjh.controller;

import com.wjh.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/test")
    public String test() {
        return testService.testService();
    }

}