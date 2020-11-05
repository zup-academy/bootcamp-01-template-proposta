package br.com.itau.cartaobrancoproposta.model;

import java.time.LocalDate;

public class BiometriaResponse {

    private final String id;
    private final String impressaoDigital;
    private final LocalDate localDate;

    public BiometriaResponse(String id, String impressaoDigital, LocalDate localDate) {
        this.id = id;
        this.impressaoDigital = impressaoDigital;
        this.localDate = localDate;
    }

    public String getId() {
        return id;
    }

    public String getImpressaoDigital() {
        return impressaoDigital;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public BiometriaResponse(Biometria biometria) {
        this.id = biometria.getId();
        this.impressaoDigital = biometria.getImpressaoDigital();
        this.localDate = biometria.getLocalDate();
    }
}
