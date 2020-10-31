package br.com.proposta.transferenciadados.respostas;

public class RespostaBloqueio {

    private String resultado;

    @Deprecated
    public RespostaBloqueio(){}

    public RespostaBloqueio(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
