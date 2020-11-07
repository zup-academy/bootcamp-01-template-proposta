package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.annotations.Base64;
import br.com.zup.proposta.model.Biometria;

import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    @Base64
    private String fingerprint;

    public Biometria toModel(){
        return new Biometria(fingerprint);
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}
