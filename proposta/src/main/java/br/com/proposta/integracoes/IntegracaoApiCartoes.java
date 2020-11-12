package br.com.proposta.integracoes;

import br.com.proposta.dtos.requests.AssociarCarteiraRequest;
import br.com.proposta.dtos.requests.AvisarViagemRequest;
import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.dtos.responses.AssociaCarteiraResponse;
import br.com.proposta.dtos.responses.AvisoViagemResponse;
import br.com.proposta.dtos.responses.BloqueioResponse;
import br.com.proposta.dtos.responses.NumeroCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@FeignClient(url = "${cartao.host}/", name = "integracaoCartao")
public interface IntegracaoApiCartoes {

    @PostMapping
    ResponseEntity<?> criarCartao(NovoCartaoRequest novoCartaoRequest);

    @GetMapping("?idProposta={idProposta}")
    ResponseEntity<NumeroCartaoResponse> buscarCartao(@PathVariable String idProposta);

    @PostMapping("{id}/bloqueios")
    ResponseEntity<BloqueioResponse> avisarLegadoBloqueioDoCartao
            (@PathVariable String id, @RequestBody @Valid BloqueioRequest bloqueioRequest);

    @PostMapping("{id}/avisos")
    ResponseEntity<AvisoViagemResponse> avisarViagem
            (@PathVariable String id, @RequestBody @Valid AvisarViagemRequest viagemRequest);

    @PostMapping("{id}/carteiras")
    ResponseEntity<AssociaCarteiraResponse> associarCarteira
            (@PathVariable String id, @RequestBody @Valid AssociarCarteiraRequest associarCarteiraRequest);

}
