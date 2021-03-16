package br.com.zup.proposta.aviso;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoRequest {
    @NotBlank
    private String destino;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataTermino;

    public Aviso toModel(HttpServletRequest httpRequest) {
        return new Aviso(destino, dataTermino,
                httpRequest.getRemoteAddr(), httpRequest.getHeader("User-Agent"));
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }
}
