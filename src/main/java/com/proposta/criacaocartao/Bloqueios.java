package com.proposta.criacaocartao;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Bloqueios {

    private String id;

    private LocalDateTime bloqueadoEm;

    private String sistemaResponsavel;

    private Boolean ativo;

    public Bloqueios(String id, LocalDateTime bloqueadoEm, String sistemaResponsavel, Boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsavel = sistemaResponsavel;
        this.ativo = ativo;
    }
}
