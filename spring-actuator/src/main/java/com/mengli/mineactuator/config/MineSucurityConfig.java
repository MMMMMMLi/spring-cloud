package com.mengli.mineactuator.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class MineSucurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                // 配置校验，当前用户必须包含 ENDPOINT_ADMIN 权限才能访问所有的端点。
                .anyRequest().hasRole("ENDPOINT_ADMIN")
                .and()
                .httpBasic();*/

        http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
                // 此处配置的校验为访问端点都放行。
                .anyRequest().permitAll();

    }
}
