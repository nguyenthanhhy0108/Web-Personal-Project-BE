package com.wjh.exception;

import com.wjh.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    private final Map<String, ErrorCode> errorMessageMap = Map.of(
            "Access Denied", ErrorCode.UNAUTHORIZED
    );


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
        if (errorMessageMap.containsKey(exception.getMessage())) {
            apiResponse.setCode(errorMessageMap.get(exception.getMessage()).getCode());
            apiResponse.setMessage(errorMessageMap.get(exception.getMessage()).getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        ApiResponse<?> apiResponse = new ApiResponse<>();
        String error = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        log.info(error);
        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        Map<String, Object> attributes = new HashMap<>();
        try {
            errorCode = ErrorCode.valueOf(error);
            var constraintViolations = exception.getBindingResult().getAllErrors()
                    .get(0).unwrap(ConstraintViolation.class);
            attributes = constraintViolations.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        } catch (IllegalArgumentException e) {
            log.error(error);
        }

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes) ?
                        mapAttribute(errorCode.getMessage(), attributes) :
                        errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes){
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));

        message = message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        message = message.replace("{" + MAX_ATTRIBUTE + "}", maxValue);

        return message;
    }
}
