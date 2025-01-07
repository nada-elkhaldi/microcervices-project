package com.project.microserviceproduits1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableConfigurationProperties
@SpringBootApplication
// pour Eureka Client
@EnableDiscoveryClient
@EnableFeignClients
public class MicroserviceProduits1Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceProduits1Application.class, args);
    }

}
