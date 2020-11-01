//package io.github.evertocnsouza.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import static org.springframework.http.HttpMethod.GET;
//import static org.springframework.http.HttpMethod.POST;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests(authorizeRequests ->
//                authorizeRequests
//                        .antMatchers(GET, "/actuator/**").permitAll()
//                        .antMatchers(GET, "/propostas/**").hasAuthority("SCOPE_propostas")
//                        .antMatchers(POST, "/propostas").hasAuthority("SCOPE_propostas")
//                        .antMatchers(POST, "/cartoes/**").hasAuthority("SCOPE_cartoes")
//                        .anyRequest().authenticated()
//        )
//                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//    }
//
//}
//
//<dependency>
//<groupId>org.springframework.boot</groupId>
//<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
//</dependency>
//
//<dependency>
//<groupId>org.springframework.security</groupId>
//<artifactId>spring-security-oauth2-jose</artifactId>
//</dependency>
//Criar o user do keycloak no final