package io.github.evertocnsouza.domain.service;

import javax.validation.constraints.NotNull;

import com.google.gson.Gson;
import io.github.evertocnsouza.rest.dto.ResultAnalisePropostaResponse;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import io.github.evertocnsouza.domain.repository.Integracoes;
import io.github.evertocnsouza.rest.dto.PropostaSolicitadaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PropostaAvaliacao {

    @Autowired
    private Integracoes integracoes;

    private final Logger logger = LoggerFactory.getLogger(Proposta.class);

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {
        String resultadoAvaliacao = integracoes.avalia(new PropostaSolicitadaRequest(proposta));

        ResultAnalisePropostaResponse resultAnalisePropostaResponseJson = new Gson().fromJson(resultadoAvaliacao, ResultAnalisePropostaResponse.class);

        logger.info("Proposta={} Status={} status de avaliação de proposta retornado com sucesso!",
                resultAnalisePropostaResponseJson.getNome(), resultAnalisePropostaResponseJson.getResultadoSolicitacao());

        return resultAnalisePropostaResponseJson.retornaStatus();
    }
}
