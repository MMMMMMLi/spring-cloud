package com.mengli.gateway;

import com.mengli.gateway.filter.SimpleFilter;
import com.mengli.gateway.filter.TokenGlobalFIlter;
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

    @Bean
    public TokenGlobalFIlter getFilter() {
        return new TokenGlobalFIlter();
    }

    // Simple
    // @Bean
    public RouteLocator producerRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        // 指定路由的路径
                        .path("/producer/**")
                        // 添加过滤器
                        .filters(f -> f
                                // 配置自定义过滤器
                                .filter(new SimpleFilter())
                                // 省略访问路径的前一个路径
                                .stripPrefix(1)
                                // 添加一些请求头
                                .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                        // 跳转的服务
                        .uri("lb://gateway-producer")
                        .order(0)
                        .id("gateway-producer")
                )
                .build();
    }

}
