package br.com.proposta.transferenciadados.requisicoes;

import br.com.proposta.entidades.Biometria;
import javax.validation.constraints.NotBlank;

public class RequisicaoBiometria {

    @NotBlank
    private String imagemBiometria;

    public RequisicaoBiometria(@NotBlank String imagemBiometria) {
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
