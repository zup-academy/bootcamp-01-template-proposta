package br.com.zup.proposta.controller;

import br.com.zup.proposta.controller.integration.Integracoes;
import br.com.zup.proposta.dto.AvaliacaoPropostaRequest;
import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.RespostaStatusAvaliacao;
import br.com.zup.proposta.model.enums.StatusAvaliacaoProposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvaliaProposta {

    @Autowired
    private Integracoes integracoes;

    public StatusAvaliacaoProposta executar(Proposta novaProposta) {

        AvaliacaoPropostaRequest resultadoAvalicao =
                integracoes.avalia(new AvaliacaoPropostaRequest(novaProposta));

        return resultadoAvalicao.getResultadoSolicitacao().normaliza();
    }
}
