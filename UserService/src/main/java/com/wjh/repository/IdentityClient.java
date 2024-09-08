package com.wjh.repository;

import com.wjh.dto.request.ProfileCreationRequest;
import com.wjh.dto.request.identity.TokenExchangeParam;
import com.wjh.dto.request.identity.UserCreationParam;
import com.wjh.dto.response.identity.TokenExchangeResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity-client", url = "${identity-provider.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/wjh-project/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam tokenExchangeParam);

    @PostMapping(value = "/admin/realms/wjh-project/users",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUserKeyCloak(@RequestHeader("authorization") String token,
                                         @RequestBody UserCreationParam userCreationParam);
}