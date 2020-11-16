package dev.arielalvesdutra.proposta.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;


@Profile("!test")
@Configuration
public class SegurancaConfiguracao extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(GET, "/actuator/**").permitAll()
                .antMatchers(GET, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:read")
                .antMatchers(GET, "/api/propostas/**").hasAuthority("SCOPE_propostas:read")
                .antMatchers(POST, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:write")
                .antMatchers(POST, "/api/propostas/**").hasAuthority("SCOPE_propostas:write")
                .anyRequest().authenticated()
                .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
