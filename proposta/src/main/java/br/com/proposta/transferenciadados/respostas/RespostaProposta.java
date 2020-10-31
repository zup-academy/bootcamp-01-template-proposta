package br.com.proposta.transferenciadados.respostas;

import br.com.proposta.entidades.Enums.StatusAvaliacaoProposta;
import br.com.proposta.entidades.Proposta;

public class RespostaProposta {

    private String nome;

    private StatusAvaliacaoProposta status;

    public RespostaProposta(Proposta proposta) {

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
