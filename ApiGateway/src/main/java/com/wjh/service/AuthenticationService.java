package com.wjh.service;

import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.request.identity.UserTokenExchangeParam;
import com.wjh.dto.response.ApiResponse;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import com.wjh.exception.ErrorNormalizer;
import com.wjh.mapper.CredentialsMapper;
import com.wjh.repository.IdentityClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private final CredentialsMapper credentialsMapper;
    private final IdentityClient identityClient;
    private final ErrorNormalizer errorNormalizer;

    @Value("${identity-provider.client-id}")
    @NonFinal
    private String clientId;

    @Value("${identity-provider.client-secret}")
    @NonFinal
    private String clientSecret;

    public ApiResponse<UserTokenExchangeResponse> exchangeUserToken(UserCredentials userCredentials) {
        UserTokenExchangeParam userTokenExchangeParam = this.credentialsMapper.toUserTokenExchangeParam(userCredentials);
        userTokenExchangeParam.setGrant_type("password");
        userTokenExchangeParam.setClient_id(this.clientId);
        userTokenExchangeParam.setClient_secret(this.clientSecret);
        userTokenExchangeParam.setScope("openid");

        UserTokenExchangeResponse userTokenExchangeResponse;
        try {
            userTokenExchangeResponse = identityClient.exchangeToken(userTokenExchangeParam);
        } catch (FeignException e) {
            throw errorNormalizer.handleKeyCloakException(e);
        }
        log.info(userTokenExchangeResponse.toString());

        return ApiResponse.<UserTokenExchangeResponse>builder()
                .data(userTokenExchangeResponse)
                .build();
    }
}
