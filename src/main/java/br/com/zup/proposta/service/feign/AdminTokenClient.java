package br.com.zup.proposta.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.configs.FeignConfig;
import br.com.zup.proposta.controllers.apiResponses.TokenResponse;
import br.com.zup.proposta.controllers.form.AdminTokenRequest;

@FeignClient(value = "admin-token", url = "${feign.admin-token-request}", configuration = FeignConfig.class)
public interface AdminTokenClient {
    
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    TokenResponse accessToken(@RequestBody AdminTokenRequest request);
}
