package io.github.evertocnsouza.rest.dto.request;

import io.github.evertocnsouza.domain.entity.Biometria;
import io.github.evertocnsouza.validation.annotation.Base64;
import javax.validation.constraints.NotNull;

public class BiometriaRequest {

    @NotNull
    @Base64
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
