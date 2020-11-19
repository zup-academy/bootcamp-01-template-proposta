package com.proposta.cadastraravisoviagem;

import javax.validation.constraints.NotBlank;

public class AvisoResponse {

    @NotBlank
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
