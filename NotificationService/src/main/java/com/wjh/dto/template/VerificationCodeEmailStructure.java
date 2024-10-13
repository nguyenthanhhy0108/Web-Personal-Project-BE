package com.wjh.dto.template;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerificationCodeEmailStructure extends EmailTemplate{

    public VerificationCodeEmailStructure() {
        this.subject = "Your verification code";
        this.message = "<html><body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">"
                + "<div style=\"max-width: 400px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f9f9f9;\">"
                + "<img src='cid:logo' alt=\"Alternative text\" style=\"width: 150px; height: auto; object-fit: cover; display: block; margin: 0 auto;\">"
                + "<hr>"
                + "<h1 style=\"text-align: left; margin-bottom: 20px;\">Your account</h1>"
                + "<p>Hello.</p>"
                + "<p>To reset your password, please verify that is you by entering the code.</p>"
                + "<p>Verification code expires in 30 minutes</p><br>"
                + "<p style=\"font-size: 18px; text-align: center;\">Your verification code is:</p>"
                + "<p style=\"font-size: 36px; text-align: center; font-weight: bold; font-family: 'Roboto Mono', monospace; color: #de29f0;\">******</p><br>"
                + "<p>Code expired? Try it again to receive a new code.</p>"
                + "<p>If you did not request this code, please ignore this. SE automatic mail cannot be created.</p>"
                + "<p>If you experience any issues regarding email verification or account creation, please contact our support: <a href=\"https://example.com/\">support.com</a></p>"
                + "<hr>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999; text-align: center;\">"
                + "This message was sent from an unmonitored email address. Please do not reply to this message."
                + "</p>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999; text-align: center;\">"
                + "© 2024 <strong>Personal Application</strong>, Inc. All rights reserved."
                + "</p>"
                + "</div>"
                + "</body></html>";
    }

    public void replaceCode(String verificationCode){
        this.message = this.message.replace("******", verificationCode);
    }
}
