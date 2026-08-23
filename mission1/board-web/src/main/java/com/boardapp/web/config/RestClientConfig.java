package com.boardapp.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.boardapp.web.global.auth.SessionAuthorizationInterceptor;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient boardApiRestClient(@Value("${board-api.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor(new SessionAuthorizationInterceptor())
                .build();
    }
}
