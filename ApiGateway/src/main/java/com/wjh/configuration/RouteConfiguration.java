package com.wjh.configuration;

import com.wjh.dto.response.ApiResponse;
import com.wjh.exception.ErrorCode;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.*;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouterFunction<ServerResponse> discoveryServerRoutes() {
        return route("discovery-server")
                .route(RequestPredicates.path("/eureka/web"),
                        http("http://localhost:8761"))
                .route(RequestPredicates.path("/eureka/**"),
                        http("http://localhost:8761"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return route("user-route")
                .GET("/user/**", http())
                .filter(lb("user-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("vehicle-inventory-circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> vehicleInventoryServiceRoutes() {
        return route("vehicle-inventory-route")
                .GET("/vehicle-inventory/**", http())
                .filter(lb("vehicle-inventory-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("vehicle-inventory-circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoutes() {
        return route("notification-route")
                .GET("/notification/**", http())
                .filter(lb("notification-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("vehicle-inventory-circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoutes() {
        return route("fallback-route")
                .GET("/fallback-route", request -> ServerResponse
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(
                            ApiResponse.<String>builder()
                                    .code(ErrorCode.SERVICE_UNAVAILABLE.getCode())
                                    .data(ErrorCode.SERVICE_UNAVAILABLE.getMessage())
                                    .build()
                        ))
                .build();
    }
}
