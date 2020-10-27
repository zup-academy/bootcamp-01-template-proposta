package br.com.proposta.dtos.requests;

import br.com.proposta.models.Biometria;

public class BiometriaRequest {

    private byte[] imagemBiometriaBase64;

    public BiometriaRequest(byte[] imagemBiometriaBase64) {
        this.imagemBiometriaBase64 = imagemBiometriaBase64;
    }

    public Biometria toModel(){
        return new Biometria(imagemBiometriaBase64);
    }

    public byte[] getImagemBiometriaBase64() {
        return imagemBiometriaBase64;
    }

    public void setImagemBiometriaBase64(byte[] imagemBiometriaBase64) {
        this.imagemBiometriaBase64 = imagemBiometriaBase64;
    }
}
