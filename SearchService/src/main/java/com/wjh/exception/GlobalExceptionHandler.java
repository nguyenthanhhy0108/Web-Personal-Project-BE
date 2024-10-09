package com.wjh.exception;

import com.wjh.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage()).build();
        return new ResponseEntity<>(apiResponse, errorCode.getHttpStatus());
    }


    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();

        log.info(exception.getMessage());

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    ResponseEntity<ApiResponse<?>> handleIllegalStateException(IllegalStateException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();
        log.info(exception.getMessage());
        apiResponse.setCode(ErrorCode.URL_PROBLEM.getCode());
        apiResponse.setMessage(ErrorCode.URL_PROBLEM.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();
        String error = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        log.info(error);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        try {
            errorCode = ErrorCode.valueOf(error);
        } catch (IllegalArgumentException e) {
            log.error(error);
        }

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }
}
