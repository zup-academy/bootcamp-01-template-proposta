package dev.arielalvesdutra.proposta.controllers.dtos;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CadastrarAvisoViagemRequestDTO {

    @NotNull(message = "{termino.obrigatorio}")
    @Future(message = "{termino.futuro}")
    private LocalDate termino;
    @NotBlank(message = "{destino.obrigatorio}")
    private String destino;

    public CadastrarAvisoViagemRequestDTO() {
    }

    public LocalDate getTermino() {
        return termino;
    }

    public CadastrarAvisoViagemRequestDTO setTermino(LocalDate termino) {
        this.termino = termino;
        return this;
    }

    public String getDestino() {
        return destino;
    }

    public CadastrarAvisoViagemRequestDTO setDestino(String destino) {
        this.destino = destino;
        return this;
    }
}
