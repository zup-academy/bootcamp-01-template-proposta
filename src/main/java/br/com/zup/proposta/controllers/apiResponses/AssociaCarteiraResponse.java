package br.com.zup.proposta.controllers.apiResponses;

import br.com.zup.proposta.controllers.apiResponses.cartao.enums.ResultadoAssociaCarteira;

public class AssociaCarteiraResponse {
    
    private ResultadoAssociaCarteira resultado;
    private String id;

    @Deprecated
    public AssociaCarteiraResponse(){}

    public AssociaCarteiraResponse(ResultadoAssociaCarteira resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public ResultadoAssociaCarteira getResultado() {
        return this.resultado;
    }

    public String getId() {
        return this.id;
    }

    public boolean isAssociada() {
        if (resultado.equals(ResultadoAssociaCarteira.ASSOCIADA)) {
            return true;
        } else {
            return false;
        }
    }
}
