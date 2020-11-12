package br.com.zup.bootcamp.proposta.api.externalsystem;

import br.com.zup.bootcamp.proposta.domain.service.enums.AnalisePropostaStatus;
import br.com.zup.bootcamp.proposta.domain.service.enums.StatusProposta;

public class ResponseAvaliacaoFiananceiraDto {

    private AnalisePropostaStatus resultadoSolicitacao;

    @Deprecated
    public ResponseAvaliacaoFiananceiraDto(){}

    public ResponseAvaliacaoFiananceiraDto(AnalisePropostaStatus resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }
    public StatusProposta getResultadoSolicitacao() {
        return resultadoSolicitacao.toPropostaStatus();
    }
}
