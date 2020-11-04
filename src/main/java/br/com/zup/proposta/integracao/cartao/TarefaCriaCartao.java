package br.com.zup.proposta.integracao.cartao;

import br.com.zup.proposta.integracao.ExecutorTransacao;
import br.com.zup.proposta.integracao.analiseproposta.StatusAvaliacaoProposta;
import br.com.zup.proposta.proposta.Proposta;
import br.com.zup.proposta.proposta.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Component
public class TarefaCriaCartao {
    @Autowired
    PropostaRepository propostaRepository;
    @Autowired
    IntegracaoCartao integracaoCartao;
    @Autowired
    ExecutorTransacao executorTransacao;

    private final Logger logger = LoggerFactory.getLogger(TarefaCriaCartao.class);

    @Scheduled(fixedDelayString = "${periodicidade.executa-operacao}")
    private void executaOperacao() {
        Collection<Proposta> propostas = propostaRepository.findByStatusAvaliacao(StatusAvaliacaoProposta.ELEGIVEL);
        propostas.forEach(this::criarCartao);
        System.out.println("Executando minha operação");
    }

    @Transactional
    private void criarCartao(@NotNull @Validated Proposta proposta) {
        logger.info("Status da proposta antes: " + proposta.getStatusAvaliacao());
        CartaoResponse cartaoResponse = integracaoCartao.pesquisaIdProposta(proposta.getId());
        Cartao cartao = cartaoResponse.toModel();
        executorTransacao.salvaEComita(cartao);
        proposta.atualizaStatus(StatusAvaliacaoProposta.APROVADA);
        proposta.incluirCartao(cartao);
        executorTransacao.atualizaEComita(proposta);
        logger.info("Cartão para proposta de id " + proposta.getId() + " criado.");
        logger.info("Status da proposta depois: " + proposta.getStatusAvaliacao().toString());
    }
}
