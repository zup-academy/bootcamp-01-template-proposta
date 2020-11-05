package br.com.cartao.proposta.domain.request;

import br.com.cartao.proposta.domain.model.AvisoViagem;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemIntegracaoRequest {

    @NotBlank
    private final String destino;
    @Future @NotNull
    private final LocalDate validoAte;

    public AvisoViagemIntegracaoRequest(@NotBlank String destino, @Future @NotNull LocalDate validoAte) {
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
