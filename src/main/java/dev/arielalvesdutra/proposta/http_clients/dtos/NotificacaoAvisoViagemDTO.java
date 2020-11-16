package dev.arielalvesdutra.proposta.http_clients.dtos;

import java.time.LocalDate;

public class NotificacaoAvisoViagemDTO {

    private String destino;
    private LocalDate validoAte;

    public NotificacaoAvisoViagemDTO() {
    }

    public String getDestino() {
        return destino;
    }

    public NotificacaoAvisoViagemDTO setDestino(String destino) {
        this.destino = destino;
        return this;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public NotificacaoAvisoViagemDTO setValidoAte(LocalDate validoAte) {
        this.validoAte = validoAte;
        return this;
    }
}
