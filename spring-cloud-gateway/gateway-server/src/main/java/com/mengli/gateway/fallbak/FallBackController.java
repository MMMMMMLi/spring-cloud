package com.mengli.gateway.fallbak;

import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    PrefixPathGatewayFilterFactory

    @RequestMapping("/fallback")
    public String fallback() {
        return "Hello This is GateWay Fallback ! ";
    }
}
