package com.wjh.service;

import com.wjh.dto.response.identity.ClientTokenExchangeResponse;
import com.wjh.repository.IdentityClient;
import lombok.experimental.NonFinal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class IdentityServiceTest {
    @Autowired
    private IdentityService identityService;

    @MockBean
    private IdentityClient identityClient;

    @Value("${test.token}")
    @NonFinal
    private String token;

    private ClientTokenExchangeResponse clientTokenExchangeResponse;

    @BeforeEach
    void setUp() {
        clientTokenExchangeResponse = ClientTokenExchangeResponse.builder()
                .accessToken(token)
                .build();
    }

    @Test
    void exchangeClientToken_success() {
        Mockito.when(identityClient.exchangeToken(any())).thenReturn(clientTokenExchangeResponse);
        var response = identityService.exchangeClientToken();

        Assertions.assertThat(response).isEqualTo(token);
    }
}
