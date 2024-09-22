package com.wjh.configuration;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${identity-provider.url}")
    @NonFinal
    private String identityProviderUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(identityProviderUrl)
                .build();
    }
}
