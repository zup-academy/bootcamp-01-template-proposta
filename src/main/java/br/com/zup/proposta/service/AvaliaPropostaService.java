package br.com.zup.proposta.service;

import br.com.zup.proposta.dto.request.SolicitacaoAnaliseRequest;
import br.com.zup.proposta.dto.response.ResultadoAnaliseResponse;
import br.com.zup.proposta.enums.StatusAvaliacaoProposta;
import br.com.zup.proposta.integration.IntegracaoProposta;
import br.com.zup.proposta.model.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaPropostaService {

    @Autowired
    private IntegracaoProposta integracoes;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) throws JsonProcessingException {

        try {
            ResultadoAnaliseResponse resultadoAvaliacao = integracoes.avaliaproposta(new SolicitacaoAnaliseRequest(proposta)).getBody();

            logger.info("Proposta={} Status={} status de avaliação de proposta retornado sem restrição!",
                    resultadoAvaliacao.getNome(), resultadoAvaliacao.getResultadoSolicitacao());

            return resultadoAvaliacao.retornaStatus();

        } catch (FeignException e) {

            if(e.status() == 422) {
                ResultadoAnaliseResponse resultadoAvaliacao =
                        new ObjectMapper().readValue(e.contentUTF8(), ResultadoAnaliseResponse.class);

                logger.info("Proposta={} Status={} status de avaliação de proposta retornado com restrição!",
                        resultadoAvaliacao.getNome(), resultadoAvaliacao.getResultadoSolicitacao());

                return resultadoAvaliacao.retornaStatus();
            }

        }
        throw (new RuntimeException());
    }
}