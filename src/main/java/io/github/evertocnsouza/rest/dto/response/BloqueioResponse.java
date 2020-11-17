package io.github.evertocnsouza.rest.dto.response;

public class BloqueioResponse {

    private String resultado;

    @Deprecated
    public BloqueioResponse(){}

    public BloqueioResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}

