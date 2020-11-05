package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.request.AvisoViagemIntegracaoRequest;
import br.com.cartao.proposta.domain.response.ResultadoAvisoViagemIntegracao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "avisoViagem", url = "${feign.url-cartao}")
public interface AvisoViagemConsumer {

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartoes/{id}/avisos")
    public ResultadoAvisoViagemIntegracao avisa(@PathVariable("id") String id, @RequestBody @Valid AvisoViagemIntegracaoRequest avisoViagemIntegracaoRequest);
}
