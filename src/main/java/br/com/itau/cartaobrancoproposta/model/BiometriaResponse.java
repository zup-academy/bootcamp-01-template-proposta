package br.com.itau.cartaobrancoproposta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BiometriaResponse {

    private final String id;
    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    @JsonProperty(value = "instanteCadastro")
    private final LocalDate localDate;

    public BiometriaResponse(String id, LocalDate localDate) {
        this.id = id;
        this.localDate = localDate;
    }

    public String getId() {
        return id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public BiometriaResponse(Biometria biometria) {
        this.id = biometria.getId();
        this.localDate = biometria.getLocalDate();
    }
}
