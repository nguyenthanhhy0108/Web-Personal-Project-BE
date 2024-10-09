package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    BRAND_NAME_IS_MISSING(9032, "Brand name is missing", HttpStatus.BAD_REQUEST),
    URL_PROBLEM(9033, "URL problem", HttpStatus.BAD_REQUEST),
    PAGE_ERROR(9034, "Invalid page parameters", HttpStatus.BAD_REQUEST),
    INVALID_PAGE_NUMBER(9035, "Invalid page number", HttpStatus.BAD_REQUEST),
    INVALID_PAGE_SIZE(9036, "Invalid page size", HttpStatus.BAD_REQUEST),

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
