package com.github.marcoscoutozup.proposta.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger instanciarLogger(){
        return LoggerFactory.getLogger(Class.class);
    }
}
