package com.github.marcoscoutozup.proposta.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .antMatchers(GET, "/actuator/**").permitAll()
                .antMatchers(GET, "/propostas/**").hasAuthority("SCOPE_propostas")
                .antMatchers(POST, "/propostas").hasAuthority("SCOPE_propostas")
                .antMatchers(POST, "/cartoes/**").hasAuthority("SCOPE_cartoes")
                .anyRequest().hasAuthority("SCOPE_admin")
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console", "/h2-console/**");
    }
}
