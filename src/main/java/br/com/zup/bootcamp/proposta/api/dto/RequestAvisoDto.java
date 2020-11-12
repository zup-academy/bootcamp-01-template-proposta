package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.domain.entity.Aviso;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;

import java.time.LocalDate;

public class RequestAvisoDto {

    @NotBlank
    private String destino;

    @Future @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;

    public Aviso toEntity(HttpServletRequest request){
        return new Aviso(validoAte, destino, request.getRemoteAddr(), request.getHeader("User-Agent"));
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

    @Override
    public String toString() {
        return "RequestAvisoDto{" +
                "destino='" + destino + '\'' +
                ", validoAte=" + validoAte +
                '}';
    }
}
