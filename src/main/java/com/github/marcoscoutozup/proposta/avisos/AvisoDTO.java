package com.github.marcoscoutozup.proposta.avisos;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoDTO {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public Aviso toAviso(HttpServletRequest request){
        return new Aviso(destino, validoAte, request.getRemoteAddr(), request.getHeader("User-Agent"));
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }
}
