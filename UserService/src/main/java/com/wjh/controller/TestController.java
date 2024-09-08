package com.wjh.controller;

import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.service.IdentityService;
import com.wjh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final IdentityService identityService;
    private final UserService userService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/token")
    public Object token() {
        userService.createProfile(ProfileCreationRequest.builder()
                        .username("test")
                        .password("test")
                        .email("test@test.com")
                        .firstName("Test")
                        .lastName("Test")
                        .dateOfBirth(LocalDate.now())
                .build());
        return identityService.exchangeClientToken();
    }
}
