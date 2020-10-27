package com.github.marcoscoutozup.proposta.biometria;

import com.github.marcoscoutozup.proposta.validator.base64.Base64;

import javax.validation.constraints.NotNull;

public class BiometriaDTO {

    @NotNull
    @Base64
    private String fingerprint;

            //1
    public Biometria toBiometria(){
        return new Biometria(fingerprint);
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}
