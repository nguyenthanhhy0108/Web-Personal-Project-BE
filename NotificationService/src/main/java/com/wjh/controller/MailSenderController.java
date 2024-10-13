package com.wjh.controller;

import com.wjh.dto.request.CredentialsRequest;
import com.wjh.service.MailSenderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/notification")
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @Deprecated
    @PostMapping("/email/{destination}")
    public void sendEmail(@PathVariable String destination,
                          @RequestBody @Valid CredentialsRequest credentialsRequest) {

        this.mailSenderService.sendAccountEmail(destination, credentialsRequest);
    }


    @GetMapping("/email/verification-code/{destination}/{verificationCode}")
    @ResponseStatus(HttpStatus.OK)
    public void sendVerificationCodeEmail(
            @PathVariable String destination,
            @PathVariable String verificationCode) {
        this.mailSenderService.sendVerificationEmail(destination, verificationCode);
    }
}
