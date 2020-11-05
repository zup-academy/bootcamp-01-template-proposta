package br.com.itau.cartaobrancoproposta.model;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private final String idCartao;
    @NotBlank
    private final String impressaoDigital;

    public BiometriaRequest(String idCartao, String impressaoDigital) {
        this.idCartao = idCartao;
        this.impressaoDigital = impressaoDigital;
    }

    public String getIdCartao() {
        return idCartao;
    }

    public String getImpressaoDigital() {
        return impressaoDigital;
    }

    public Biometria toModel() {
        return new Biometria(this.idCartao, this.impressaoDigital);
    }
}
