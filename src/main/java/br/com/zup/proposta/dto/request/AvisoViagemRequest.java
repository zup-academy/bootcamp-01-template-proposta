package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.AvisoViagem;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull @Future
    private LocalDate validoAte;

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public AvisoViagem toModel(HttpServletRequest request){
        return new AvisoViagem(destino, validoAte, request.getRemoteAddr(), request.getHeader("User-Agent"));
    }
}
