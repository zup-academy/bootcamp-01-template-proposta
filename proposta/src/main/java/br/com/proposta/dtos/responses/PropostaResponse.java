package br.com.proposta.dtos.responses;

import br.com.proposta.models.Enums.StatusAvaliacaoProposta;
import br.com.proposta.models.Proposta;

public class PropostaResponse {

    private String nome;

    private StatusAvaliacaoProposta status;

    public PropostaResponse(Proposta proposta) {

        this.nome = proposta.getNome();
        this.status = proposta.getStatus();

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public StatusAvaliacaoProposta getStatus() {
        return status;
    }

    public void setStatus(StatusAvaliacaoProposta status) {
        this.status = status;
    }

}
