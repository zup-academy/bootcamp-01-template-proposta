package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.enums.EstadoAvisoViagemIntegracao;

public class ResultadoAvisoViagemIntegracao {

    private EstadoAvisoViagemIntegracao resultado;

    @Deprecated
    public ResultadoAvisoViagemIntegracao() {
    }

    public ResultadoAvisoViagemIntegracao(EstadoAvisoViagemIntegracao resultado) {
        this.resultado = resultado;
    }

    public EstadoAvisoViagemIntegracao getResultado() {
        return resultado;
    }

    public void setResultado(EstadoAvisoViagemIntegracao resultado) {
        this.resultado = resultado;
    }
}
