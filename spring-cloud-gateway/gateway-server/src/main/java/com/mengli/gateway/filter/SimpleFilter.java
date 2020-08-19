package com.mengli.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class SimpleFilter implements GatewayFilter, Ordered {

    private static final String ELAPSED_TIME_BEGIN = "elapsedTimeBegin";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 给ServerWebExchange添加一下当前系统时间
        exchange.getAttributes().put(ELAPSED_TIME_BEGIN, System.currentTimeMillis());

        return chain.filter(exchange)
                // 这个之前的是指生命周期为 pre 的操作
                .then(
                // 这个之后的是指生命周期为 post 的操作
                Mono.fromRunnable(() -> {
                    // 具体操作
                    Long startTime = exchange.getAttribute(ELAPSED_TIME_BEGIN);
                    if (startTime != null) {
                        System.out.println((exchange.getRequest().getURI() + ": " + (System.currentTimeMillis() - startTime) + "ms"));
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
