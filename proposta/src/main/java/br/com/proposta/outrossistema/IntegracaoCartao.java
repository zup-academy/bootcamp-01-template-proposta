package br.com.proposta.outrossistema;

import br.com.proposta.dtos.requests.NovoCartaoRequest;
import br.com.proposta.models.Cartao;
import com.google.gson.JsonObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(url = "${cartao.host}/", name = "integracaoCartao")
public interface IntegracaoCartao {

    @PostMapping
    ResponseEntity<?> criarCartao(NovoCartaoRequest novoCartaoRequest);

    @GetMapping("?idProposta={idProposta}")
    ResponseEntity<?> buscarCartao(@PathVariable("idProposta") Long idProposta);

}
