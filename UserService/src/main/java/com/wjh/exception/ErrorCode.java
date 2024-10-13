package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_IS_MISSING(9001, "Username is missing", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_MISSING(9002, "Password is missing", HttpStatus.BAD_REQUEST),
    USER_EXISTED(9003, "Username existed, please choose another one", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(9004, "Email existed, please choose another one", HttpStatus.BAD_REQUEST),
    INVALID_DOB(9005, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(9006, "Your username must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(9007, "Your password must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    USERNAME_EMAIL_NOT_MATCH(9037, "Email and username not match", HttpStatus.BAD_REQUEST),
    REQUEST_MUST_NOT_A_BLANK(9038, "Request must not a blank", HttpStatus.BAD_REQUEST),
    WRONG_VERIFICATION_CODE(9039, "Invalid verification code", HttpStatus.BAD_REQUEST),
    EXPIRED_VERIFICATION_CODE(9040, "Expiry verification code", HttpStatus.BAD_REQUEST),

    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    public static ErrorCode getByMessage(String message) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getMessage().equals(message)) {
                return errorCode;
            }
        }
        return null;
    }
}
