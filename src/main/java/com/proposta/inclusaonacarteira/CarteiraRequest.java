package com.proposta.inclusaonacarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public Carteiras toModel(TipoCarteira novaCarteira) {
        return new Carteiras(email, novaCarteira);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCarteira() {
        return carteira;
    }

    public void setCarteira(String carteira) {
        this.carteira = carteira;
    }
}
