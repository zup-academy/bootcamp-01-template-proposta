package br.com.proposta.services;

import br.com.proposta.dtos.requests.AssociaCarteiraRequest;
import br.com.proposta.dtos.requests.AvisoViagemRequest;
import br.com.proposta.dtos.requests.BloqueioRequest;
import br.com.proposta.dtos.requests.NovoCartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${cartao.host}/", name = "integracaoCartao")
public interface IntegracaoCartaoService {

    @PostMapping
    ResponseEntity<?> criarCartao(NovoCartaoRequest novoCartaoRequest);

    @GetMapping("?idProposta={idProposta}")
    String buscarCartao(@PathVariable String idProposta);

    @PostMapping("{id}/bloqueios")
    String avisarLegadoBloqueioDoCartao(@PathVariable String id, @RequestBody BloqueioRequest bloqueioRequest);

    @PostMapping("{id}/avisos")
    String avisarViagem(@PathVariable String id, @RequestBody AvisoViagemRequest viagemRequest);

    @PostMapping("{id}/carteiras")
    String associarCarteira(@PathVariable String id, @RequestBody AssociaCarteiraRequest associaCarteiraRequest);

}
