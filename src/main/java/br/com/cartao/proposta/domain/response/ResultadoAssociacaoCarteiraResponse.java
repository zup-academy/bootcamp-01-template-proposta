package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.enums.EstadoAssociacaoCarteiraIntegracao;

public class ResultadoAssociacaoCarteiraResponse {

    private final EstadoAssociacaoCarteiraIntegracao resultado;
    private final String id;

    public ResultadoAssociacaoCarteiraResponse(EstadoAssociacaoCarteiraIntegracao resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public EstadoAssociacaoCarteiraIntegracao getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
