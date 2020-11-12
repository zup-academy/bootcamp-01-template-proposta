package br.com.zup.proposta.carteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {
    @Email
    @NotBlank
    private String email;

    private Carteira toModel(TipoCarteira tipoCarteira) {
        return new Carteira(email, tipoCarteira);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
