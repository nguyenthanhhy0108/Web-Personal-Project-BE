package com.wjh.service;

import com.wjh.dto.request.BannerRequest;
import com.wjh.dto.request.CredentialsRequest;
import com.wjh.dto.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailSenderService {

    private JavaMailSender sender;

    public EmailContent prepareEmail(String toEmail, EmailTemplate emailTemplate) {
        MimeMessage message = sender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject(emailTemplate.getSubject());
            helper.setSentDate(new Date());
            helper.setText(emailTemplate.getMessage(), true);
            helper.addInline("logo", new ClassPathResource("/static/images/logo.png"));
            if (emailTemplate.getMessage().contains("bannerImage")) {
                helper.addInline("bannerImage", new ClassPathResource("/static/images/banner.jpg"));
            }

            return new EmailContent(message, helper);
        }catch (Exception exception){
            log.error(exception.getMessage());
            return null;
        }
    }


    public void sendEmail(String toEmail, EmailTemplate emailTemplate)  {

        MimeMessage message = this.prepareEmail(toEmail, emailTemplate).message();

        CompletableFuture.runAsync(() -> sender.send(message));
    }


    public void sendEmailWithAttachment(String toEmail, EmailTemplate emailTemplate,
                                        String attachmentFilename, InputStreamSource attachment)
            throws MessagingException {

        EmailContent emailContent = prepareEmail(toEmail, emailTemplate);
        MimeMessage message = emailContent.message();
        emailContent.helper().addAttachment(attachmentFilename, attachment);

        CompletableFuture.runAsync(() -> sender.send(message));

    }


    public void sendDepositeContractEmail(String toEmail, String encodedPdf, String customerName, String contractId)
            throws MessagingException {
        ContractEmailStructure contractEmailStructure = new ContractEmailStructure(customerName, contractId);
        byte[] pdfBytes = Base64.getDecoder().decode(encodedPdf);
        InputStreamSource attachment = new ByteArrayResource(pdfBytes);
        System.out.println(attachment);
        this.sendEmailWithAttachment(toEmail, contractEmailStructure, "Contract.pdf", attachment);
    }


    public void sendAccountEmail(String toEmail, CredentialsRequest credentialsRequest) {
        AccountEmailStructure accountEmailStructure =
                new AccountEmailStructure(credentialsRequest.getUsername(), credentialsRequest.getPassword());
        this.sendEmail(toEmail, accountEmailStructure);
    }


    public void sendAnnouncementEmail(String toEmail, BannerRequest bannerRequest) {
        AnnouncementEmailStructure announcementEmailStructure = new AnnouncementEmailStructure(
                bannerRequest.getBannerTitle(),
                bannerRequest.getBannerDescription(),
                bannerRequest.getBannerUrl());

        this.sendEmail(toEmail, announcementEmailStructure);
    }


    public void sendVerificationEmail(String toEmail, String verificationCode) {
        VerificationCodeEmailStructure verificationCodeEmailStructure =
                new VerificationCodeEmailStructure();

        verificationCodeEmailStructure.replaceCode(verificationCode);
        this.sendEmail(toEmail, verificationCodeEmailStructure);
    }
}
