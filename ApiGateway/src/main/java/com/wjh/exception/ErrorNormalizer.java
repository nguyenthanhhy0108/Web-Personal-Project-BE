package com.wjh.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjh.dto.response.identity.KeyCloakError;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ErrorNormalizer {
    private final ObjectMapper objectMapper;
    private final Map<String, ErrorCode> errorCodeMap;

    public ErrorNormalizer() {
        objectMapper = new ObjectMapper();
        errorCodeMap = new HashMap<>();

        errorCodeMap.put("Invalid user credentials", ErrorCode.WRONG_CREDENTIALS);
        errorCodeMap.put("Code not valid", ErrorCode.GOOGLE_CODE_INVALID);
        errorCodeMap.put("Incorrect redirect_uri", ErrorCode.GOOGLE_INVALID_REDIRECT_URI);
    }

    public AppException handleKeyCloakException(FeignException exception) {

        if (exception.status() == 401) {
            return AppException.builder()
                    .errorCode(ErrorCode.WRONG_CREDENTIALS)
                    .build();
        }

        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}