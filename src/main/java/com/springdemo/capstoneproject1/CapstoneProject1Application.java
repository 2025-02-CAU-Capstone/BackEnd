package com.springdemo.capstoneproject1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
public class CapstoneProject1Application {

    public static void main(String[] args) {
        SpringApplication.run(CapstoneProject1Application.class, args);
    }

}
