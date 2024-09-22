package com.wjh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable);

        serverHttpSecurity.cors(cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOrigin("http://localhost:3000");
                    corsConfig.addAllowedMethod(CorsConfiguration.ALL);
                    corsConfig.addAllowedHeader(CorsConfiguration.ALL);
                    return corsConfig;
                })
        );

        serverHttpSecurity.authorizeExchange(auth ->
                auth.pathMatchers("/eureka/**").permitAll()
                        .pathMatchers("/app/**").permitAll()
                        .pathMatchers("/user/create-user").permitAll()
                        .anyExchange().permitAll())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults()));
        return serverHttpSecurity.build();
    }
}
