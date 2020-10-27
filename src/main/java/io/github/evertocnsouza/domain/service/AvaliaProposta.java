package io.github.evertocnsouza.domain.service;

import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.rest.dto.DocumentoRequest;
import io.github.evertocnsouza.domain.repository.Integracoes;
import io.github.evertocnsouza.domain.enums.RespostaStatusAvaliacao;
import io.github.evertocnsouza.domain.enums.StatusAvaliacaoProposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {

    @Autowired
    private Integracoes integracoes;

    public StatusAvaliacaoProposta executa(@NotNull @Validated Proposta proposta) {

        String resultadoAvaliacao = integracoes
                .avalia(new DocumentoRequest(proposta));

        return RespostaStatusAvaliacao.valueOf(resultadoAvaliacao).getStatusAvaliacao();
    }

}