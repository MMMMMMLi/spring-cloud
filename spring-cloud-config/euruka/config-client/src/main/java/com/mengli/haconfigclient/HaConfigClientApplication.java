package com.mengli.haconfigclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HaConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaConfigClientApplication.class, args);
    }

}
