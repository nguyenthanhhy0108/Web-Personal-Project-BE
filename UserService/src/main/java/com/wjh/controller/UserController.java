package com.wjh.controller;

import com.wjh.dto.request.ForgotPasswordConfirmationRequest;
import com.wjh.dto.request.ForgotPasswordRequest;
import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.ProfileCreationResponse;
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
    public ResponseEntity<ApiResponse<ProfileCreationResponse>> createUser(
            @RequestBody @Valid ProfileCreationRequest profileCreationRequest) {
        log.info("Creating profile");
        ProfileCreationResponse profileCreationResponse = userService.createProfile(profileCreationRequest);
        ApiResponse<ProfileCreationResponse> apiResponse = ApiResponse.<ProfileCreationResponse>builder()
                .data(profileCreationResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
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
