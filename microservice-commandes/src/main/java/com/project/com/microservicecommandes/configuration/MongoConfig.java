package com.project.com.microservicecommandes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                // Convertisseur pour écrire LocalDateTime dans MongoDB
                new Converter<LocalDateTime, Date>() {
                    @Override
                    public Date convert(LocalDateTime source) {
                        return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
                    }
                },
                // Convertisseur pour lire LocalDateTime depuis MongoDB
                new Converter<Date, LocalDateTime>() {
                    @Override
                    public LocalDateTime convert(Date source) {
                        return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    }
                },
                // Convertisseur pour écrire LocalDate dans MongoDB
                new Converter<LocalDate, Date>() {
                    @Override
                    public Date convert(LocalDate source) {
                        return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }
                },
                // Convertisseur pour lire LocalDate depuis MongoDB
                new Converter<Date, LocalDate>() {
                    @Override
                    public LocalDate convert(Date source) {
                        return source.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                }
        ));
    }
}
