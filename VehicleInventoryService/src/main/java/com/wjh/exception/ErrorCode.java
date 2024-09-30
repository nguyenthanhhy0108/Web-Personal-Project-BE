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
    VEHICLE_NOT_EXIST(9013, "Vehicle not exist", HttpStatus.BAD_REQUEST),
    VEHICLE_LESS_THAN_DESIRE(9014, "Vehicle in inventory less than your desire", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER(9015, "Invalid vehicle amount, at least is {min}", HttpStatus.BAD_REQUEST),
    BLANK_BRAND_NAME(9016, "Brand name can not a blank", HttpStatus.BAD_REQUEST),
    BLANK_VEHICLE_NAME(9017, "Vehicle name can not a blank", HttpStatus.BAD_REQUEST),
    BLANK_VEHICLE_PRICE(9018, "Vehicle price can not a blank", HttpStatus.BAD_REQUEST),
    BLANK_VEHICLE_IMAGE(9019, "Vehicle image can not a blank", HttpStatus.BAD_REQUEST),
    BLANK_VEHICLE_DESCRIPTION(9020, "Vehicle description can not a blank", HttpStatus.BAD_REQUEST),
    DELETING_ERROR(9021, "Deleting error", HttpStatus.INTERNAL_SERVER_ERROR),
    FOREIGN_KEY_ERROR(9022, "An error relate to foreign key", HttpStatus.BAD_REQUEST),
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
