package com.wjh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        // Authorization rules
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/eureka/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/vehicle-inventory/brands/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/vehicle-inventory/brands").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/vehicle-inventory/brands/*").authenticated()
                .requestMatchers(HttpMethod.POST, "/vehicle-inventory/vehicles").authenticated()
                .requestMatchers(HttpMethod.PUT, "/vehicle-inventory/vehicles/amount").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/vehicle-inventory/vehicles/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/vehicle-inventory/vehicles/**").permitAll()
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
