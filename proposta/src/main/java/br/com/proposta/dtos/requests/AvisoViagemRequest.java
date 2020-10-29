package br.com.proposta.dtos.requests;

import java.time.OffsetDateTime;

public class AvisoViagemRequest {

    private String destino;

    private OffsetDateTime validoAte;

    public AvisoViagemRequest(String destino, OffsetDateTime validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public OffsetDateTime getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(OffsetDateTime validoAte) {
        this.validoAte = validoAte;
    }
}
