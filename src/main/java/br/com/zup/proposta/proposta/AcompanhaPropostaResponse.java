package br.com.zup.proposta.proposta;

import br.com.zup.proposta.integracao.analiseproposta.StatusAvaliacaoProposta;

public class AcompanhaPropostaResponse {
    private StatusAvaliacaoProposta status;
    private String id;

    public AcompanhaPropostaResponse(Proposta proposta) {
        this.status = proposta.getStatusAvaliacao();
        this.id = proposta.getId();
    }

    public StatusAvaliacaoProposta getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
