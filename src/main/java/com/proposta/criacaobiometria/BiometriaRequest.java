package com.proposta.criacaobiometria;

import com.proposta.criacaocartao.Cartao;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BiometriaRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9+/]*={1,2}$")
    private String fingerPrint;

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(cartao.getId(), fingerPrint);
    }

}

