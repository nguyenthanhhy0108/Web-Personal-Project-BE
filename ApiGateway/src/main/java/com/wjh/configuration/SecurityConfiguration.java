package com.wjh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final String[] freeResourceUrls = {
            "/app/**",
            "/user/profiles",
            "/user/reset-password/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // CORS configuration
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration corsConfig = new CorsConfiguration();
            corsConfig.addAllowedOrigin("http://localhost:3000");
            corsConfig.addAllowedMethod(CorsConfiguration.ALL);
            corsConfig.addAllowedHeader(CorsConfiguration.ALL);
            return corsConfig;
        }));

        // Authorization rules
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/eureka/**").permitAll()
                .requestMatchers(freeResourceUrls).permitAll()
                .requestMatchers(HttpMethod.GET, "/notification/**").permitAll()
                .requestMatchers("/search/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/vehicle-inventory/brands/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/vehicle-inventory/brands").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/vehicle-inventory/brands/*").authenticated()
                .requestMatchers(HttpMethod.POST, "/vehicle-inventory/vehicles").authenticated()
                .requestMatchers(HttpMethod.PUT, "/vehicle-inventory/vehicles/amount").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/vehicle-inventory/vehicles/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/vehicle-inventory/vehicles/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/vehicle-inventory/vehicles/*/*").permitAll()
                .requestMatchers("/fallback-route").permitAll()
                .anyRequest().authenticated()
                );
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
        ));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(new CustomAuthoritiesConverter());
        return converter;
    }
}
