package br.com.itau.cartaobrancoproposta.model;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaRequest {

    @NotBlank
    private String impressaoDigital;

    @Deprecated
    public BiometriaRequest() {
    }

    public BiometriaRequest(@NotBlank String impressaoDigital) {
        this.impressaoDigital = impressaoDigital;
    }

    public String getImpressaoDigital() {
        return impressaoDigital;
    }

    public Biometria toModel() {
        String impressaoDigitalCodificada = Base64.getEncoder().encodeToString(this.impressaoDigital.getBytes());
        return new Biometria(impressaoDigitalCodificada);
    }
}
