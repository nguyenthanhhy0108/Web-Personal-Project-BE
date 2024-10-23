package com.wjh.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    FOREIGN_KEY_ERROR(9022, "An error relate to foreign key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(9998, "You do not have enough permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(9005, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    BLANK_REQUEST(9038, "Request must not a blank", HttpStatus.BAD_REQUEST),
    DELETING_ERROR(9021, "Deleting error", HttpStatus.INTERNAL_SERVER_ERROR),
    SAVE_CONTRACT_FAIL(9042, "Save contract fail", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CONTRACT_ID(9043, "Invalid contract id", HttpStatus.INTERNAL_SERVER_ERROR),
    GET_CONTRACT_FAIL(9044, "Get contract fail", HttpStatus.INTERNAL_SERVER_ERROR),
    VEHICLE_OUT_OF_STOCK(9045, "Vehicle out of stock", HttpStatus.INTERNAL_SERVER_ERROR),
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
