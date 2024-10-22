package com.wjh.controller;

import com.wjh.dto.request.ForgotPasswordConfirmationRequest;
import com.wjh.dto.request.ForgotPasswordRequest;
import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.ProfileResponse;
import com.wjh.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/profiles")
    public ResponseEntity<ApiResponse<ProfileResponse>> createUser(
            @RequestBody @Valid ProfileCreationRequest profileCreationRequest) {
        log.info("Creating profile");
        ProfileResponse profileResponse = userService.createProfile(profileCreationRequest);
        ApiResponse<ProfileResponse> apiResponse = ApiResponse.<ProfileResponse>builder()
                .data(profileResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profiles/{userId}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserByUserId(@PathVariable String userId) {
        log.info("Retrieving profile");
        return ResponseEntity.ok(ApiResponse.<ProfileResponse>builder()
                        .data(this.userService.findProfileByUserId(userId))
                .build());
    }


    @GetMapping("/emails")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<String>>> getNecessaryEmails() {
        log.info("Getting necessary emails");
        return ResponseEntity.ok(ApiResponse.<List<String>>builder()
                .data(this.userService.findAllNecessaryEmail())
                .build());
    }

    @PostMapping("/reset-password/verify")
    @ResponseStatus(HttpStatus.OK)
    public void sendVerificationCode(@RequestBody @Valid ForgotPasswordRequest request) {
        log.info("Sending verification code");
        this.userService.sendVerificationEmail(request);
    }


    @PostMapping("/reset-password/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void sendVerificationCode(@RequestBody @Valid ForgotPasswordConfirmationRequest request) {
        this.userService.confirmResetPassword(request);
    }
}
