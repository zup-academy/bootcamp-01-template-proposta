package dev.arielalvesdutra.proposta.services.dtos;

import dev.arielalvesdutra.proposta.entities.enums.CarteiraTipo;

public class AssociarCarteiraDTO {

    private String email;
    private CarteiraTipo tipo;

    public AssociarCarteiraDTO() {
    }

    public String getEmail() {
        return email;
    }

    public AssociarCarteiraDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public CarteiraTipo getTipo() {
        return tipo;
    }

    public AssociarCarteiraDTO setTipo(CarteiraTipo tipo) {
        this.tipo = tipo;
        return this;
    }
}
