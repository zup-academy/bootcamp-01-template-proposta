package com.proposta.criacaocartao;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Carteiras {

    private String id;

    private String email;

    private LocalDateTime associadaEm;

    private String emissor;

    public Carteiras(String id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }
}
