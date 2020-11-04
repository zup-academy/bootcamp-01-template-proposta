package br.com.zup.proposta.integracao.cartao.biometria;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaRequest {
    @NotBlank
    private String digital;

    public Biometria toBiometria() {
        return new Biometria(Base64.getEncoder().encodeToString(digital.getBytes()));
    }

    public String getDigital() {
        return digital;
    }
}
