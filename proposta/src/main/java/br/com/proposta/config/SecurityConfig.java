package br.com.proposta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;


//Contagem de Pontos - TOTAL:0

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter  {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority("SCOPE_meu-primeiro-escopo")
                                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority("SCOPE_meu-primeiro-escopo")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}