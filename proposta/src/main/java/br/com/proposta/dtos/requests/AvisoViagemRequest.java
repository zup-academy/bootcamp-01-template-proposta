package br.com.proposta.dtos.requests;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @Future
    private OffsetDateTime validoAte;

    public AvisoViagemRequest(@NotBlank String destino, @Future OffsetDateTime validoAte) {
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
