package br.com.zup.proposta.controllers.form;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.zup.proposta.model.enums.Carteiras;

public class AssociaCarteiraForm {
    
    @Enumerated(EnumType.STRING)
    private Carteiras carteira;

    public AssociaCarteiraForm(){}

    public AssociaCarteiraForm(Carteiras carteira) {
        this.carteira = carteira;
    }

    public Carteiras getCarteira() {
        return this.carteira;
    }
    
}
