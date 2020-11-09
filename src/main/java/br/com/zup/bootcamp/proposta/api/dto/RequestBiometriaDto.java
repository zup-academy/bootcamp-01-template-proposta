package br.com.zup.bootcamp.proposta.api.dto;

import br.com.zup.bootcamp.proposta.api.handler.Base64;
import br.com.zup.bootcamp.proposta.domain.entity.Biometria;

import javax.validation.constraints.NotNull;

public class RequestBiometriaDto {

    @Base64 @NotNull
    private byte[] fingerPrint;

    @Deprecated
    public RequestBiometriaDto(){}

    public RequestBiometriaDto(byte[] fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public byte[] getFingerPrint() {
        return fingerPrint;
    }

    public Biometria toEntity(){
        return new Biometria(fingerPrint);
    }
}
