package com.github.marcoscoutozup.proposta.cartao.bloqueio;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Bloqueio {

    private LocalDate bloqueadoEm;
    private String sistemaResponsavel;
    private boolean ativo;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(LocalDate bloqueadoEm, String sistemaResponsavel, boolean ativo) {
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Bloqueio{" +
                "bloqueadoEm=" + bloqueadoEm +
                ", sistemaResponsavel='" + sistemaResponsavel + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
