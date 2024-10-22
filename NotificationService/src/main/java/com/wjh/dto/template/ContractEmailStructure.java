package com.wjh.dto.template;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractEmailStructure extends EmailTemplate{

    public ContractEmailStructure(String actualName, String actualCode) {
        this.subject = "Your contract";
        this.message = "<html><body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;\">"
                + "<div style=\"max-width: 400px; margin: 20px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; background-color: #f9f9f9;\">"
                + "<img src='cid:logo' alt=\"Alternative text\" style=\"width: 150px; height: auto; object-fit: cover; display: block; margin: 0 auto;\">"
                + "<hr>"
                + "<h1 style=\"text-align: left; margin-bottom: 20px;\">Your account</h1>"
                + "<p>Hello {name}.</p>"
                + "<p>This is your vehicle deposit contract <i>({code})</i>.</p>"
                + "<p>Thank you for using our service.</p>"
                + "<p>If you experience any issues , please contact our support: <a href=\"https://example.com/\">support.com</a></p>"
                + "<hr>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999; text-align: center;\">"
                + "This message was sent from an unmonitored email address. Please do not reply to this message."
                + "</p>"
                + "<p style=\"font-family: Arial, sans-serif; font-size: 10px; color: #999; text-align: center;\">"
                + "© 2024 <strong>Personal Application</strong>, Inc. All rights reserved."
                + "</p>"
                + "</div>"
                + "</body></html>";
        this.message = this.replaceName(this.message, actualName);
        this.message = this.replaceCode(this.message, actualCode);
    }

    public String replaceName(String message, String actualName) {
        return message.replace("{name}", actualName);
    }

    public String replaceCode(String message, String actualCode) {
        return message.replace("{code}", actualCode);
    }
}
