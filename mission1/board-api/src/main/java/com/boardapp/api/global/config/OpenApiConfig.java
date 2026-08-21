package com.boardapp.api.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI boardApiOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Board API")
                        .description("게시판 서비스 REST API")
                        .version("v1"));
    }
}
