package br.com.zup.proposta.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.zup.proposta.controllers.apiResponses.AnaliseResponse;
import br.com.zup.proposta.controllers.form.AnaliseRequestForm;

@FeignClient(value = "analise", url = "${feign.analise-url}")
public interface AnaliseClient {
    
    @RequestMapping(method = RequestMethod.POST, value = "api/solicitacao/")
    AnaliseResponse analiseProposta(@RequestBody AnaliseRequestForm form);
}
