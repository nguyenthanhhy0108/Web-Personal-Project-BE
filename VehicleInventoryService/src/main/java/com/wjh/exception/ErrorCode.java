package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    BRAND_EXISTED(9011, "Vehicle brand existed", HttpStatus.BAD_REQUEST),
    BRAND_NOT_EXIST(9012, "Vehicle brand not exist", HttpStatus.BAD_REQUEST),
    VEHICLE_NOT_EXIST(9013, "Vehicle brand not exist", HttpStatus.BAD_REQUEST),
    VEHICLE_LESS_THAN_DESIRE(9014, "Vehicle in inventory less than your desire", HttpStatus.BAD_REQUEST),
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
