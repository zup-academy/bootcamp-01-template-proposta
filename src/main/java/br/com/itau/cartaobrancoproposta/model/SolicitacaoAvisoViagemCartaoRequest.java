package br.com.itau.cartaobrancoproposta.model;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class SolicitacaoAvisoViagemCartaoRequest {

    @NotBlank
    private final String destino;
    @NotBlank
    private final LocalDate validoAte;

    public SolicitacaoAvisoViagemCartaoRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
