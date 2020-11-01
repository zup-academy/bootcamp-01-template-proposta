package com.proposta.retornoproposta;

import com.proposta.criacaoproposta.Proposta;
import com.proposta.criacaoproposta.StatusProposta;

public class PropostaResponse {

    private Long id;

    private StatusProposta status;

    private Boolean cartaoCadastrado;

    public PropostaResponse(Proposta proposta) {
        id = proposta.getId();
        status = proposta.getStatus();
        cartaoCadastrado = proposta.getCartao() != null;
    }

    public Long getId() {
        return id;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public Boolean getCartaoCadastrado() {
        return cartaoCadastrado;
    }
}
