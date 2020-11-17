package io.github.evertocnsouza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(GET, "/actuator/**").permitAll()
                .antMatchers(GET, "/propostas/**").hasAuthority("SCOPE_meu-primeiro-escopo")
                .antMatchers(POST, "/propostas").hasAuthority("SCOPE_meu-primeiro-escopo")
                .antMatchers(POST, "/cartoes/**").hasAuthority("SCOPE_meu-primeiro-escopo")
                .anyRequest().hasAuthority("SCOPE_admin")
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console", "/h2-console/**");
    }
}

