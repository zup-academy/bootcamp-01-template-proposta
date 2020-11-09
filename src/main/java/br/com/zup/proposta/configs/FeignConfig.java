package br.com.zup.proposta.configs;

import org.springframework.context.annotation.Bean;

import feign.codec.Encoder;
import feign.form.FormEncoder;

public class FeignConfig {

    @Bean
    public Encoder encoder() {
        return new FormEncoder();
    }
}
