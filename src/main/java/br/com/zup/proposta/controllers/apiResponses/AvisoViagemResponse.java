package br.com.zup.proposta.controllers.apiResponses;

import br.com.zup.proposta.configs.exceptions.ApiException;
import br.com.zup.proposta.controllers.apiResponses.cartao.enums.ResultadoAvisoViagem;

public class AvisoViagemResponse {
    
    private ResultadoAvisoViagem resultado;

    @Deprecated
    public AvisoViagemResponse(){}

    public AvisoViagemResponse(ResultadoAvisoViagem resultado) {
        this.resultado = resultado;
    }

    public ResultadoAvisoViagem getResultado() {
        return this.resultado;
    }

    public boolean isCriado() {
        if (this.resultado.equals(ResultadoAvisoViagem.CRIADO)) {
            return true;
        } else if (this.resultado.equals(ResultadoAvisoViagem.FALHA)) {
            return false;
        }

        throw new ApiException("Valor inesperado para ResultadoAvisoViagem.");
    }
}
