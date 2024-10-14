package com.wjh.repository;

import com.wjh.configuration.FeignClientInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "notification-service", configuration = FeignClientInterceptor.class)
public interface NotificationClient {

    @GetMapping("/notification/email/verification-code/{destination}/{verificationCode}")
    void sendVerificationEmail(
            @PathVariable("destination") String destination,
            @PathVariable("verificationCode") String verificationCode);
}
