package com.boardapp.web.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.boardapp.web.board.client.feign")
public class FeignScanConfig {
}
