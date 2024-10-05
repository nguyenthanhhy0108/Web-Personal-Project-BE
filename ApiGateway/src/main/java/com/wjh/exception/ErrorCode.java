package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SERVICE_UNAVAILABLE(10000, "Service unavailable, please try again later.", HttpStatus.SERVICE_UNAVAILABLE),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    WRONG_CREDENTIALS(9008, "Wrong credentials", HttpStatus.UNAUTHORIZED),
    GOOGLE_CODE_INVALID(9009, "Google code is invalid", HttpStatus.BAD_REQUEST),
    GOOGLE_INVALID_REDIRECT_URI(9010, "Google redirect URI is invalid", HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
