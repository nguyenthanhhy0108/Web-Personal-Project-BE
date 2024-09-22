package com.wjh.controller;

import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import com.wjh.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/get-tokens")
    public Mono<ResponseEntity<ApiResponse<String>>> getAccessToken(@RequestBody UserCredentials userCredentials) {
        return authenticationService.exchangeUserToken(userCredentials)
                .map(UserTokenExchangeResponse::getAccessToken)
                .map(token -> {
                    ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                            .data(token)
                            .build();

                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                });
    }
}
