package com.boardapp.web.board.client.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;
import tools.jackson.databind.ObjectMapper;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return new BoardFeignErrorDecoder(objectMapper);
    }
}
