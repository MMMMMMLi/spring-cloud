package com.mengli.gatewayconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayConsumerApplication.class, args);
    }

}
