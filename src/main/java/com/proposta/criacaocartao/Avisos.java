package com.proposta.criacaocartao;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class Avisos {

    private Date validoAte;

    private String destino;

    public Avisos(Date validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }
}
