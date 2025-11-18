package com.springdemo.capstoneproject1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // Swagger 기본 정보
        Info info = new Info()
                .title("P2L API Documentation")
                .description("문제 이미지 기반 강의 매핑 시스템 API")
                .version("1.0.0");

        // HTTPS 서버 URL 강제
        Server server = new Server()
                .url("https://13-209-30-220.nip.io")
                .description("P2L HTTPS Server");

        return new OpenAPI()
                .info(info)
                .addServersItem(server);   // 이게 핵심!
    }
}