package br.com.cartao.proposta.consumer;

import br.com.cartao.proposta.domain.request.SolicitacaoInclusaoCarteiraRequest;
import br.com.cartao.proposta.domain.response.ResultadoAssociacaoCarteiraResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "carteiraDigital", url = "${feign.url-cartao}")
public interface AssociarCarteiraDigitalConsumer {

    @RequestMapping(method = RequestMethod.POST, path = "/api/cartoes/{id}/carteiras")
    public ResultadoAssociacaoCarteiraResponse associaCarteiraDigital(@PathVariable("id") String idCartao, @RequestBody @Valid SolicitacaoInclusaoCarteiraRequest solicitacaoInclusaoCarteiraRequest);
}
