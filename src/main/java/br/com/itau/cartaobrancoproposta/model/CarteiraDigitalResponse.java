package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CarteiraDigitalResponse {

    private final String emissor;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private final LocalDateTime associadaEm;

    public CarteiraDigitalResponse(String emissor, LocalDateTime associadaEm) {
        this.emissor = emissor;
        this.associadaEm = associadaEm;
    }

    public String getEmissor() {
        return emissor;
    }

    public LocalDateTime getAssociadaEm() {
        return associadaEm;
    }
}
