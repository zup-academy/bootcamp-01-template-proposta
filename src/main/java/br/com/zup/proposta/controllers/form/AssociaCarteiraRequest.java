package br.com.zup.proposta.controllers.form;

import br.com.zup.proposta.model.enums.Carteiras;

public class AssociaCarteiraRequest {
    
    private String email;
    private Carteiras carteira;

    public AssociaCarteiraRequest(String email, Carteiras carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return this.email;
    }

    public Carteiras getCarteira() {
        return this.carteira;
    }

}
