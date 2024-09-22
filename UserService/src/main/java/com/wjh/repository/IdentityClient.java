package com.wjh.repository;

import com.wjh.dto.request.identity.ClientTokenExchangeParam;
import com.wjh.dto.request.identity.UserCreationParam;
import com.wjh.dto.response.identity.ClientTokenExchangeResponse;
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
    ClientTokenExchangeResponse exchangeToken(@QueryMap ClientTokenExchangeParam clientTokenExchangeParam);

    @PostMapping(value = "/admin/realms/wjh-project/users",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUserKeyCloak(@RequestHeader("authorization") String token,
                                         @RequestBody UserCreationParam userCreationParam);
}
