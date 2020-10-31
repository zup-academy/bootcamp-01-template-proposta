package br.com.proposta.integracoes;

import br.com.proposta.transferenciadados.requisicoes.RequisicaoAssociarCarteira;
import br.com.proposta.transferenciadados.requisicoes.RequisicaoAvisarViagem;
import br.com.proposta.transferenciadados.requisicoes.RequisicaoBloqueio;
import br.com.proposta.transferenciadados.requisicoes.RequisicaoNovoCartao;
import br.com.proposta.transferenciadados.respostas.RespostaAssociacaoCarteira;
import br.com.proposta.transferenciadados.respostas.RespostaAvisoViagem;
import br.com.proposta.transferenciadados.respostas.RespostaBloqueio;
import br.com.proposta.transferenciadados.respostas.RespostaNumeroCartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(url = "${cartao.host}/", name = "integracaoCartao")
public interface IntegracaoApiCartoes {

    @PostMapping
    ResponseEntity<?> criarCartao(RequisicaoNovoCartao requisicaoNovoCartao);

    @GetMapping("?idProposta={idProposta}")
    ResponseEntity<RespostaNumeroCartao> buscarCartao(@PathVariable String idProposta);

    @PostMapping("{id}/bloqueios")
    ResponseEntity<RespostaBloqueio> avisarLegadoBloqueioDoCartao(@PathVariable String id, @RequestBody RequisicaoBloqueio requisicaoBloqueio);

    @PostMapping("{id}/avisos")
    ResponseEntity<RespostaAvisoViagem> avisarViagem(@PathVariable String id, @RequestBody RequisicaoAvisarViagem viagemRequest);

    @PostMapping("{id}/carteiras")
    ResponseEntity<RespostaAssociacaoCarteira> associarCarteira(@PathVariable String id, @RequestBody RequisicaoAssociarCarteira requisicaoAssociarCarteira);

}
