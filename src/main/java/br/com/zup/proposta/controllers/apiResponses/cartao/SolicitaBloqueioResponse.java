package br.com.zup.proposta.controllers.apiResponses.cartao;

import br.com.zup.proposta.controllers.apiResponses.cartao.enums.BloqueioResultado;

public class SolicitaBloqueioResponse {
    
    private BloqueioResultado resultado;

    @Deprecated
    public SolicitaBloqueioResponse(){}

    public SolicitaBloqueioResponse(BloqueioResultado resultado) {
        this.resultado = resultado;
    }

    public BloqueioResultado getResultado() {
        return this.resultado;
    }

    public boolean isBloqueado() {
        if (this.resultado.equals(BloqueioResultado.BLOQUEADO)) {
            return true;
        }
        return false;
    }
}
