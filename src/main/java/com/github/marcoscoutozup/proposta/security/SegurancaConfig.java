package com.github.marcoscoutozup.proposta.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SegurancaConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(SegurancaConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(GET, "/actuator/health").permitAll()
                .antMatchers(GET, "/proposta/**").hasAuthority("SCOPE_propostas")
                .antMatchers(POST, "/proposta").hasAuthority("SCOPE_propostas")
                .antMatchers(POST, "/biometrias/cadastrar/**").hasAuthority("SCOPE_biometrias")
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
