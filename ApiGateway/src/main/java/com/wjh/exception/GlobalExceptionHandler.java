package com.wjh.exception;

import com.wjh.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final Map<String, ErrorCode> errorMessageMap = Map.of(
            "Access Denied", ErrorCode.UNAUTHORIZED
    );

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.error(errorCode.getMessage());
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage()).build();
        return new ResponseEntity<>(apiResponse, errorCode.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();

        log.info(exception.getMessage());
        if (errorMessageMap.containsKey(exception.getMessage())) {
            apiResponse.setCode(errorMessageMap.get(exception.getMessage()).getCode());
            apiResponse.setMessage(errorMessageMap.get(exception.getMessage()).getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
