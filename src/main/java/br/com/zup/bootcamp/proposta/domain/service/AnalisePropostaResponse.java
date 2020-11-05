package br.com.zup.bootcamp.proposta.domain.service;

public class AnalisePropostaResponse {

    private AnalisePropostaStatus resultadoSolicitacao;

    public AnalisePropostaStatus getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public void setResultadoSolicitacao(AnalisePropostaStatus resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }
}
