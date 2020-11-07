package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class BloqueioResponse {

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private final LocalDateTime localDateTime;
    private final EstadoBloqueio estadoBloqueio;

    public BloqueioResponse(LocalDateTime localDateTime, EstadoBloqueio estadoBloqueio) {
        this.localDateTime = localDateTime;
        this.estadoBloqueio = estadoBloqueio;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public EstadoBloqueio getEstadoBloqueio() {
        return estadoBloqueio;
    }
}
