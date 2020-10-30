package io.github.evertocnsouza.domain.service;

import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.domain.repository.IntegracaoProposta;
import io.github.evertocnsouza.rest.dto.response.SolicitacaoAnaliseProposta;
import io.github.evertocnsouza.rest.dto.response.ResultadoAnaliseProposta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class PropostaAvaliacao {

    @Autowired
    private IntegracaoProposta integracaoProposta;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {

        ResultadoAnaliseProposta resultadoAnalisePropostaJson =
                integracaoProposta.avalia(new SolicitacaoAnaliseProposta(proposta)).getBody();

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",

                resultadoAnalisePropostaJson.getNome(), resultadoAnalisePropostaJson.getResultadoSolicitacao());

        return resultadoAnalisePropostaJson.retornaStatus();
    }
}
