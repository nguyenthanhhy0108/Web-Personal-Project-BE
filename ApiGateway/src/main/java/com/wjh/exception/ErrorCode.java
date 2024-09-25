package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_CREDENTIALS(9000, "Wrong credentials", HttpStatus.UNAUTHORIZED),
    GOOGLE_CODE_INVALID(9001, "Google code is invalid", HttpStatus.BAD_REQUEST),
    GOOGLE_INVALID_REDIRECT_URI(9002, "Google redirect URI is invalid", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
