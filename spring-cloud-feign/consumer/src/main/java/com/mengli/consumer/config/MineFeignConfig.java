package com.mengli.consumer.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MineFeignConfig {

    /**
     * 这样在一个配置类中声明之后，在指定的Feign客户端声明此配置，则是针对某个客户端来实现。
     *
     * 如果在启动类中声明，则是针对所有的Feign客户端。
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
        /**
         * NONE：
         *      不记录任何信息
         * BASIC：
         *      仅记录请求方法、URL以及响应状态码和执行时间
         * HEADERS：
         *      除了记录BASIC级别的信息外，还会记录请求和响应的头信息
         * FULL：
         *      记录所有请求与响应的明细，包括头信息、请求头、元数据等。
         */
    }

}
