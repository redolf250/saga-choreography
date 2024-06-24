package com.redolf.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("user-service",predicateSpec -> predicateSpec
                        .path("/api/v1/order/**")
                        .uri("http://user-service"))
                .route("payment-service",predicateSpec -> predicateSpec
                        .path("/api/v1/payment/**")
                        .uri("lb://payment-service"))
                .route("admin-server",predicateSpec -> predicateSpec
                        .path("/monitoring")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/applications"))
                        .uri("http://localhost:8084/applications"))
//                .route("admin-server-static",predicateSpec -> predicateSpec
//                        .path("/admin/**")
//                        .uri("http://localhost:8084/applications"))
                .route("discovery-server",predicateSpec -> predicateSpec
                        .path("/eureka/web")
                        .filters(gatewayFilterSpec -> gatewayFilterSpec.setPath("/"))
                        .uri("http://localhost:8761"))
                .route("discovery-server-static",predicateSpec -> predicateSpec
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .route("notification-service",predicateSpec -> predicateSpec
                        .path("/notification/**")
                        .uri("lb://notification-service"))
                .build();
    }
}
