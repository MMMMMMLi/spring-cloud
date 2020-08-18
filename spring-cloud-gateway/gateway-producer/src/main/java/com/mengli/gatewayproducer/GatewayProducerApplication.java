package com.mengli.gatewayproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayProducerApplication.class, args);
    }

}
