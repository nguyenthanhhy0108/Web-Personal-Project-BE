package com.wjh.controller;

import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.response.ApiResponse;
import com.wjh.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/token")
    public ResponseEntity<ApiResponse<String>> getAccessToken(@RequestBody UserCredentials userCredentials) {
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .data(this.authenticationService.exchangeUserToken(userCredentials).getData().getAccessToken())
                .build());
    }
}
