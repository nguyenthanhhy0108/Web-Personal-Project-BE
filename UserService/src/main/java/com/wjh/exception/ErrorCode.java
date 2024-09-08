package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_IS_MISSING(1001, "Username is missing", HttpStatus.BAD_REQUEST),
    PASSWORD_IS_MISSING(1002, "Password is missing", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1003, "Username existed, please choose another one", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1004, "Email existed, please choose another one", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1005, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1006, "Your username must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1007, "Your password must be between {min} and {max} characters", HttpStatus.BAD_REQUEST),
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
