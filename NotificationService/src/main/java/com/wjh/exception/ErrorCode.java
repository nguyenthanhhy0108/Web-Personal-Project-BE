package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    SAVE_BANNER_FAIL(9023, "Persisting banner fail", HttpStatus.INTERNAL_SERVER_ERROR),
    IMAGE_NOT_EXISTED(9024, "Image not exist", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETING_ERROR(9025, "Deleting error", HttpStatus.INTERNAL_SERVER_ERROR),
    BLANK_BANNER_TITLE(9026, "Banner title must not a blank", HttpStatus.BAD_REQUEST),
    BLANK_BANNER_DESCRIPTION(9027, "Banner description must not a blank", HttpStatus.BAD_REQUEST),
    BLANK_BANNER_URL(9028, "Banner url must not a blank", HttpStatus.BAD_REQUEST),
    BLANK_USERNAME(9029, "Username must not a blank", HttpStatus.BAD_REQUEST),
    BLANK_PASSWORD(9030, "Password must not a blank", HttpStatus.BAD_REQUEST),
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
