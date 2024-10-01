package com.wjh.controller;

import com.wjh.dto.request.CredentialsRequest;
import com.wjh.service.MailSenderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/notification")
public class MailSenderController {

    private final MailSenderService mailSenderService;

    @PostMapping("/email/{destination}")
    public void sendEmail(@PathVariable String destination,
                          @RequestBody @Valid CredentialsRequest credentialsRequest) {

        this.mailSenderService.sendAccountEmail(destination, credentialsRequest);
    }
}
