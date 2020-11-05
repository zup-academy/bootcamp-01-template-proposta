package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.api.handler.Base64;
import br.com.zup.bootcamp.proposta.domain.entity.Biometria;

import javax.validation.constraints.NotNull;

public class RequestBiometriaDto {

    @Base64 @NotNull
    private final String fingerPrint;

    public RequestBiometriaDto(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public Biometria toEntity(){
        return new Biometria(fingerPrint);
    }
}
