package com.proposta.bloqueiodecartao;

import javax.validation.constraints.NotBlank;

public class BloqueioResponse {

    @NotBlank
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
