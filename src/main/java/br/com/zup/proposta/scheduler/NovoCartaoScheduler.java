package br.com.zup.proposta.scheduler;

import br.com.zup.proposta.dao.ExecutorTransacao;
import br.com.zup.proposta.dao.repository.PropostaRepository;
import br.com.zup.proposta.dto.CartaoRequest;
import br.com.zup.proposta.dto.integration.IntegracoesCartao;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NovoCartaoScheduler {

    private ExecutorTransacao executorTransacao; //1
    private IntegracoesCartao integracoesCartao; //2
    private PropostaRepository propostaRepository; //3
    private Logger logger = LoggerFactory.getLogger(NovoCartaoScheduler.class);

    public NovoCartaoScheduler(ExecutorTransacao executorTransacao,
                               IntegracoesCartao integracoesCartao,
                               PropostaRepository propostaRepository) {
        this.executorTransacao = executorTransacao;
        this.integracoesCartao = integracoesCartao;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelayString = "${periodo.proposta.cartao}")
    public void verificaCartao(){

        List<Proposta> propostas = propostaRepository
                .buscarPropostasElegiveis(StatusAvaliacaoProposta.ELEGIVEL); //4

            for (Proposta proposta: propostas){ //5
                CartaoRequest cartaoRequest = integracoesCartao
                        .buscarCartaoPorIdProposta(
                                new CartaoRequest(proposta.getId().toString())
                                        .getIdProposta()); //6

                logger.info("Cartão gerado com sucesso!{}", cartaoRequest);

                proposta.associarCartao(cartaoRequest.getNumero());

                executorTransacao.atualizaEComita(proposta);

                logger.info("Proposta associada:{}", proposta);
            }

    }

}
