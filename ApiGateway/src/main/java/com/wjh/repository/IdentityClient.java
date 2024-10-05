package com.wjh.repository;

import com.wjh.dto.request.identity.UserTokenExchangeParam;
import com.wjh.dto.response.identity.UserTokenExchangeResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "identity-client", url = "${identity-provider.url}")
public interface IdentityClient {
    @PostMapping(value = "/realms/wjh-project/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    UserTokenExchangeResponse exchangeToken(@QueryMap UserTokenExchangeParam param);
}
