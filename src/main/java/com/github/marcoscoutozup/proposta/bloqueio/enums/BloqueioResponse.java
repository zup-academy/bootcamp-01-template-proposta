package com.github.marcoscoutozup.proposta.bloqueio.enums;

import javax.validation.constraints.NotNull;

public class BloqueioResponse {

    @NotNull
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
