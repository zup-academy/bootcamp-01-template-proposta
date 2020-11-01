package br.com.zup.proposta.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //desabilitando a protecao csrf
                .authorizeRequests() //autoriza requisições
                .anyRequest().permitAll() //permite todas as requisições
                .and()
                .httpBasic(); //autenticação basica http
    }

}
