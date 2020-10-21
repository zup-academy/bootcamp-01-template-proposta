package com.github.marcoscoutozup.proposta.cartao.aviso;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Aviso {

    private LocalDate validoAte;
    private String destino;

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    @Override
    public String toString() {
        return "Aviso{" +
                "validoAte=" + validoAte +
                ", destino='" + destino + '\'' +
                '}';
    }
}
