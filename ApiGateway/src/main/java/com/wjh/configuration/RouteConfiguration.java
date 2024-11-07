package com.wjh.configuration;

import com.wjh.dto.response.ApiResponse;
import com.wjh.exception.ErrorCode;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
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
    public RouterFunction<ServerResponse> searchServiceRoutes() {
        return GatewayRouterFunctions.route("search-route")
                .route(RequestPredicates.path("/search/**"), http())
                .filter(lb("search-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoutes() {
        return GatewayRouterFunctions.route("user-route")
                .route(RequestPredicates.path("/user/**"), http())
                .filter(lb("user-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> vehicleInventoryServiceRoutes() {
        return GatewayRouterFunctions.route("vehicle-inventory-route")
                .route(RequestPredicates.path("/vehicle-inventory/**"), http())
                .filter(lb("vehicle-inventory-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoutes() {
        return GatewayRouterFunctions.route("notification-route")
                .route(RequestPredicates.path("/notification/**"), http())
                .filter(lb("notification-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoutes() {
        return GatewayRouterFunctions.route("order-route")
                .route(RequestPredicates.path("/deposite-contract/**"), http())
                .filter(lb("order-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> chatServiceRoutes() {
        return GatewayRouterFunctions.route("chat-route")
                .route(RequestPredicates.path("/chat/**"), http())
                .filter(lb("chat-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("chat-service-circuit-breaker",
                        URI.create("forward:/fallback-route")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoutes() {
        return RouterFunctions
                .route(RequestPredicates.path("/fallback-route"),
                        request -> ServerResponse
                                .status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body(
                                        ApiResponse.<String>builder()
                                                .code(ErrorCode.SERVICE_UNAVAILABLE.getCode())
                                                .data(ErrorCode.SERVICE_UNAVAILABLE.getMessage())
                                                .build()
                                ));
    }
}
