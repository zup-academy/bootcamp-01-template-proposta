package br.com.proposta.dtos.requests;

import br.com.proposta.models.Biometria;

public class BiometriaRequest {

    private String imagemBiometria;

    public BiometriaRequest(String imagemBiometria) {
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
