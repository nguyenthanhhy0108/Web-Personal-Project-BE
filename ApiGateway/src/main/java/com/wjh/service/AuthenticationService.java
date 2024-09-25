package com.wjh.service;

import com.wjh.dto.request.identity.GoogleCodeParam;
import com.wjh.dto.request.identity.UserCredentials;
import com.wjh.dto.request.identity.UserTokenExchangeParam;
import com.wjh.dto.request.identity.UserTokenExchangeParamWithGoogleCode;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import com.wjh.exception.ErrorNormalizer;
import com.wjh.mapper.CredentialsMapper;
import com.wjh.mapper.GoogleExchangeTokenMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private final CredentialsMapper credentialsMapper;
    private final GoogleExchangeTokenMapper googleExchangeTokenMapper;
    private final WebClient webClient;
    private final ErrorNormalizer errorNormalizer;

    @Value("${identity-provider.client-id}")
    @NonFinal
    private String clientId;

    @Value("${identity-provider.client-secret}")
    @NonFinal
    private String clientSecret;

    public Mono<UserTokenExchangeResponse> exchangeUserToken(UserCredentials userCredentials) {
        UserTokenExchangeParam userTokenExchangeParam = this.credentialsMapper.toUserTokenExchangeParam(userCredentials);
        userTokenExchangeParam.setGrant_type("password");
        userTokenExchangeParam.setClient_id(this.clientId);
        userTokenExchangeParam.setClient_secret(this.clientSecret);
        userTokenExchangeParam.setScope("openid");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", userTokenExchangeParam.getGrant_type());
        formData.add("client_id", userTokenExchangeParam.getClient_id());
        formData.add("client_secret", userTokenExchangeParam.getClient_secret());
        formData.add("scope", userTokenExchangeParam.getScope());
        formData.add("username", userCredentials.getUsername());
        formData.add("password", userCredentials.getPassword());

        return webClient.post()
                .uri("/realms/wjh-project/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(UserTokenExchangeResponse.class)
                .onErrorMap(WebClientResponseException.class, errorNormalizer::handleKeyCloakException);
    }

    public Mono<UserTokenExchangeResponse> exchangeUserTokenWithGoogleCode(GoogleCodeParam googleCodeParam) {

        UserTokenExchangeParamWithGoogleCode userTokenExchangeParamWithGoogleCode =
                this.googleExchangeTokenMapper.toUserTokenExchangeParam(googleCodeParam);

        userTokenExchangeParamWithGoogleCode.setClient_id(this.clientId);
        userTokenExchangeParamWithGoogleCode.setClient_secret(this.clientSecret);
        userTokenExchangeParamWithGoogleCode.setGrant_type("authorization_code");

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", userTokenExchangeParamWithGoogleCode.getGrant_type());
        formData.add("client_id", userTokenExchangeParamWithGoogleCode.getClient_id());
        formData.add("client_secret", userTokenExchangeParamWithGoogleCode.getClient_secret());
        formData.add("redirect_uri", userTokenExchangeParamWithGoogleCode.getRedirect_uri());
        formData.add("code", userTokenExchangeParamWithGoogleCode.getCode());

        return webClient.post()
                .uri("/realms/wjh-project/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(UserTokenExchangeResponse.class)
                .onErrorMap(WebClientResponseException.class, errorNormalizer::handleKeyCloakException);
    }
}
