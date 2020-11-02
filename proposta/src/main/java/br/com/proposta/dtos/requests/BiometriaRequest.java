package br.com.proposta.dtos.requests;

import br.com.proposta.entidades.Biometria;
import javax.validation.constraints.NotBlank;

public class BiometriaRequest {

    @NotBlank
    private String imagemBiometria;

    @Deprecated
    public BiometriaRequest(){}

    public BiometriaRequest(@NotBlank String imagemBiometria) {
        this.imagemBiometria = imagemBiometria;
    }

    public Biometria toModel(){

        return new Biometria(imagemBiometria);

    }

    public String getImagemBiometria() {
        return imagemBiometria;
    }

    public void setImagemBiometria(String imagemBiometria) {
        this.imagemBiometria = imagemBiometria;
    }
}
