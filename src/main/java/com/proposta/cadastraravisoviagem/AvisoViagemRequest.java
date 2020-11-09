package com.proposta.cadastraravisoviagem;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate dataTerminoViagem;

    public AvisoViagemRequest(@NotBlank String destino, @NotNull LocalDate dataTerminoViagem) {
        this.destino = destino;
        this.dataTerminoViagem = dataTerminoViagem;
    }
    public AvisoViagem toModel(String user, String ip) {
        return new AvisoViagem(user, ip, destino, dataTerminoViagem);
    }
}
