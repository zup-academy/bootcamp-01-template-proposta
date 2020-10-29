package br.com.proposta.dtos.responses;

public class AvisoViagemResponse {

    private String resultado;

    public AvisoViagemResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
