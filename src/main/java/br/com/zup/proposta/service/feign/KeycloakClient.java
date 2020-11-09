package br.com.zup.proposta.service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.controllers.apiResponses.KeycloakUser;
import br.com.zup.proposta.controllers.form.NovaSenhaUsuarioForm;
import br.com.zup.proposta.controllers.form.NovoUsuarioForm;

@FeignClient(value = "keycloak", url = "${feign.admin.criar-user-url}")
public interface KeycloakClient {
    
    @RequestMapping(method = RequestMethod.GET, path = "/users?username={username}")
    List<KeycloakUser> getUser(@RequestHeader("Authorization") String token, @PathVariable String username);

    @RequestMapping(method = RequestMethod.PUT, path = "/users/{id}/reset-password")
    void setPassword(@RequestHeader("Authorization") String token, @PathVariable String id, 
        @RequestBody NovaSenhaUsuarioForm form);

    @RequestMapping(method = RequestMethod.POST, path = "/users")
    void criaUsuario(@RequestHeader("Authorization") String token, @RequestBody NovoUsuarioForm form);
}
