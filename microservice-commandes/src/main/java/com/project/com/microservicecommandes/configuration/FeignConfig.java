package com.project.com.microservicecommandes.configuration;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Retryer retryer() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        // propagation des erreurs
        return new ErrorDecoder.Default();
    }
}


