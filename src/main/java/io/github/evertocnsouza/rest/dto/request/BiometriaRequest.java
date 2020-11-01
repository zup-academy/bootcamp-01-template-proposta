package io.github.evertocnsouza.rest.dto.request;

import io.github.evertocnsouza.domain.entity.Biometria;

import javax.validation.constraints.NotNull;

public class BiometriaRequest {

    @NotNull
    private String digital;

    public Biometria toBiometria(){
        return new Biometria(digital);
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public String getDigital() {
        return digital;
    }
}
