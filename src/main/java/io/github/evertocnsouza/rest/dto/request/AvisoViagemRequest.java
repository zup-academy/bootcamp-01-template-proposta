package io.github.evertocnsouza.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.evertocnsouza.domain.entity.AvisoViagem;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    String destino;

    @NotNull
    @Future
    @JsonFormat(pattern= "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;

    public AvisoViagem toAviso(HttpServletRequest request){
    return new AvisoViagem(destino, validoAte, request.getRemoteAddr(), request.getHeader("User-AGent")); }

    public String getDestino() { return destino; }

    public void setDestino(String destino) { this.destino = destino; }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public void setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
    }
}
