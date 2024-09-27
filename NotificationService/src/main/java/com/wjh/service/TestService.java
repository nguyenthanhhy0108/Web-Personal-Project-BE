package com.wjh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TestService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public TestService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String testService() {
        WebClient webClient = webClientBuilder.build();

        return webClient.get()
                .uri("http://user-service/user/test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
