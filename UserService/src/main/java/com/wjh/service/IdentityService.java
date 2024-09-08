package com.wjh.service;

import com.wjh.dto.request.identity.TokenExchangeParam;
import com.wjh.dto.response.identity.TokenExchangeResponse;
import com.wjh.repository.IdentityClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class IdentityService {

    private final IdentityClient identityClient;

    @Value("${identity-provider.client-id}")
    @NonFinal
    private String clientID;

    @Value("${identity-provider.client-secret}")
    @NonFinal
    private String clientSecret;

    public String exchangeClientToken() {
        var clientTokenObject = identityClient.exchangeToken(TokenExchangeParam.builder()
                        .client_id(clientID)
                        .client_secret(clientSecret)
                        .scope("openid")
                        .grant_type("client_credentials")
                .build());

        log.info("client token: {}", clientTokenObject);

        return clientTokenObject.getAccessToken();
    }
}
