package br.com.zup.cartaoproposta.entities.cartao.aviso;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Contagem de carga intrínseca da classe: 0
 */

public class AvisoNovoRequest {

    @NotNull
    @Future
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;
    @NotBlank
    private String destino;

    @Deprecated
    public AvisoNovoRequest(){}

    public AvisoNovoRequest(@NotNull @Future LocalDate validoAte, @NotBlank String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
