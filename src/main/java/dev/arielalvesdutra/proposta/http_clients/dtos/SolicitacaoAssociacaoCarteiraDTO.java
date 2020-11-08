package dev.arielalvesdutra.proposta.http_clients.dtos;

import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;

public class SolicitacaoAssociacaoCarteiraDTO {

    private String email;
    private CarteiraTipo carteira;

    public SolicitacaoAssociacaoCarteiraDTO() {
    }

    public String getEmail() {
        return email;
    }

    public SolicitacaoAssociacaoCarteiraDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public CarteiraTipo getCarteira() {
        return carteira;
    }

    public SolicitacaoAssociacaoCarteiraDTO setCarteira(CarteiraTipo carteira) {
        this.carteira = carteira;
        return this;
    }
}
