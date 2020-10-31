package br.com.proposta.integracoes;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoSolicitarAnaliseDaProposta;
import br.com.proposta.transferenciadados.respostas.RespostaAnaliseDeProposta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${avaliacao.host}", name = "integracoes")
public interface IntegracaoApiAnalise {

    @PostMapping
    ResponseEntity<RespostaAnaliseDeProposta> avaliaproposta(@RequestBody RequisicaoSolicitarAnaliseDaProposta requisicaoSolicitarAnaliseDaProposta);

}
