package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destinoViagem;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataTerminoViagem;

    @Deprecated
    public AvisoViagemRequest() {
    }

    public AvisoViagemRequest(@NotBlank String destinoViagem, LocalDate dataTerminoViagem) {
        this.destinoViagem = destinoViagem;
        this.dataTerminoViagem = dataTerminoViagem;
    }

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public AvisoViagem toModel(String ipCliente, String userAgent) {
        return new AvisoViagem(this.dataTerminoViagem, this.destinoViagem, ipCliente, userAgent);
    }
}
