package com.wjh.service;

import com.wjh.dto.request.identity.GoogleCodeParam;
import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${test.access-token}")
    private String token;
    private UserCredentials userCredentials;
    private GoogleCodeParam googleCodeParam;
    private UserTokenExchangeResponse userTokenExchangeResponse;
    @Mock
    private WebClient webClient;

    @BeforeEach
    public void initData() {
        userCredentials = UserCredentials.builder()
                .username("test")
                .password("test")
                .build();

        userTokenExchangeResponse = new UserTokenExchangeResponse();
        userTokenExchangeResponse.setAccessToken(token);

        googleCodeParam = GoogleCodeParam.builder()
                .code("abc")
                .redirectUri("abc")
                .build();
    }

    @Test
    void exchangeUserToken_validRequest_success() {
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri("/realms/wjh-project/protocol/openid-connect/token")).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(UserTokenExchangeResponse.class)).thenReturn(Mono.just(userTokenExchangeResponse));

        Mono<UserTokenExchangeResponse> result = authenticationService.exchangeUserToken(userCredentials);
        result.subscribe(response -> assertEquals(token, response.getAccessToken()));
    }

    @Test
    void exchangeUserToken_invalidRequest_fail() {
        userCredentials.setUsername("test_fail");

        AppException appException;

        appException = AppException.builder()
                .errorCode(ErrorCode.WRONG_CREDENTIALS)
                .build();

        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri("/realms/wjh-project/protocol/openid-connect/token")).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(UserTokenExchangeResponse.class)).thenThrow(appException);

        ApiResponse<AppException> apiResponse = new ApiResponse<>(
                appException.getErrorCode().getCode(),
                appException.getErrorCode().getMessage(),
                appException
        );
        Mono<ApiResponse<AppException>> exception = Mono.just(apiResponse);
        exception.subscribe(response -> {
            assertEquals(9000, response.getCode());
            assertEquals("Wrong credentials", response.getMessage());
        });
    }

    @Test
    void exchangeUserTokenWithGoogleCode_validRequest_success() {
        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri("/realms/wjh-project/protocol/openid-connect/token")).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(UserTokenExchangeResponse.class)).thenReturn(Mono.just(userTokenExchangeResponse));

        Mono<UserTokenExchangeResponse> result = authenticationService.exchangeUserTokenWithGoogleCode(googleCodeParam);
        result.subscribe(response -> assertEquals(token, response.getAccessToken()));
    }

    @Test
    void exchangeUserTokenWithGoogleCode_invalidRequest_fail() {

        AppException appException;

        appException = AppException.builder()
                .errorCode(ErrorCode.UNCATEGORIZED_EXCEPTION)
                .build();

        WebClient.RequestHeadersSpec requestHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = Mockito.mock(WebClient.RequestBodyUriSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.post()).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri("/realms/wjh-project/protocol/openid-connect/token")).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.body(any())).thenReturn(requestHeadersSpec);
        Mockito.when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.bodyToMono(UserTokenExchangeResponse.class)).thenThrow(appException);

        ApiResponse<AppException> apiResponse = new ApiResponse<>(
                appException.getErrorCode().getCode(),
                appException.getErrorCode().getMessage(),
                appException
        );
        Mono<ApiResponse<AppException>> exception = Mono.just(apiResponse);
        exception.subscribe(response -> {
            assertEquals(9999, response.getCode());
        });
    }
}
