package com.wjh.service;

import com.wjh.dto.request.BannerRequest;
import com.wjh.dto.request.CredentialsRequest;
import com.wjh.dto.template.AccountEmailStructure;
import com.wjh.dto.template.AnnouncementEmailStructure;
import com.wjh.dto.template.EmailTemplate;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailSenderService {

    private JavaMailSender sender;

    public void sendEmail(String toEmail, EmailTemplate emailTemplate)  {

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

        }catch (Exception exception){
            log.error(exception.getMessage());
        }
        sender.send(message);
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
}
