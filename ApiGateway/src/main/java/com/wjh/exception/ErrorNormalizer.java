package com.wjh.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjh.dto.response.identity.KeyCloakError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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

    public AppException handleKeyCloakException(WebClientResponseException exception) {

        KeyCloakError response = new KeyCloakError();
        try {
            log.warn("Parsing may be incompletely");
            response = objectMapper.readValue(exception.getResponseBodyAsString(), KeyCloakError.class);

            log.error(response.getErrorDescription());

            return new AppException(errorCodeMap.get(response.getErrorDescription()));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        log.error(response.toString());

        return new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
}