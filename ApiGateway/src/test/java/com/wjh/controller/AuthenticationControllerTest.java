package com.wjh.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjh.dto.request.identity.GoogleCodeParam;
import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import com.wjh.exception.AppException;
import com.wjh.exception.ErrorCode;
import com.wjh.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Slf4j
@AutoConfigureWebTestClient
public class AuthenticationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${test.access-token}")
    private String token;

    @MockBean
    private AuthenticationService authenticationService;

    private UserCredentials userCredentials;
    private GoogleCodeParam googleCodeParam;
    private UserTokenExchangeResponse userTokenExchangeResponse;

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
    /*
    * Change token in properties file when you need
    * */
    void getAccessToken_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCredentials);

        Mockito.when(authenticationService.exchangeUserToken(any()))
                .thenReturn(Mono.just(userTokenExchangeResponse));

        //WHEN //THEN
        webTestClient.post()
                .uri("/app/get-tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("1000");
    }


    @Test
        /*
         * Change token in properties file when you need
         * */
    void getAccessToken_invalidCredentials_fail() throws Exception {
        //GIVEN
        /*
        * You can change for invalid password or both :D
        * */
        userCredentials.setUsername("test_fail");

        AppException appException;

        appException = AppException.builder()
                .errorCode(ErrorCode.WRONG_CREDENTIALS)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCredentials);

        Mockito.when(authenticationService.exchangeUserToken(any()))
                .thenThrow(appException);

        //WHEN //THEN
        webTestClient.post()
                .uri("/app/get-tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody()
                .jsonPath("code").isEqualTo("9000");
    }

    @Test
        /*
         * Change token in properties file when you need
         * */
    void getAccessTokenByGoogleCode_validRequest_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(googleCodeParam);

        Mockito.when(authenticationService.exchangeUserTokenWithGoogleCode(any()))
                .thenReturn(Mono.just(userTokenExchangeResponse));

        //WHEN //THEN
        webTestClient.post()
                .uri("/app/get-tokens-by-google-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.code").isEqualTo("1000");
    }

    @Test
        /*
         * Change token in properties file when you need
         * */
    void getAccessTokenByGoogleCode_invalidRequest_fail() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(googleCodeParam);

        AppException appException;

        appException = AppException.builder()
                .errorCode(ErrorCode.GOOGLE_CODE_INVALID)
                .build();

        Mockito.when(authenticationService.exchangeUserTokenWithGoogleCode(any()))
                .thenThrow(appException);

        //WHEN //THEN
        webTestClient.post()
                .uri("/app/get-tokens-by-google-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(content)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.code").isEqualTo("9001");
    }
}
