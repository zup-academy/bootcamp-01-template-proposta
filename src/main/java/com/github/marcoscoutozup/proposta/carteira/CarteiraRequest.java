package com.github.marcoscoutozup.proposta.carteira;

import com.github.marcoscoutozup.proposta.carteira.enums.TipoCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    public Carteira toCarteira(TipoCarteira carteira){
        return new Carteira(email, carteira);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
