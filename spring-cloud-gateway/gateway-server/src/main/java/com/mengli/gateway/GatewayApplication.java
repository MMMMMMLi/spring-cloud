package com.mengli.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // Simple
    @Bean
    public RouteLocator producerRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/producer/**")
                        .filters(f -> f.stripPrefix(2)
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        .uri("lb://gateway-producer")
                        .order(0)
                        .id("fluent_customer_service")
                )
                .build();
    }

}
