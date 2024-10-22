package com.wjh.dto.template;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

public record EmailContent(MimeMessage message, MimeMessageHelper helper) {
}