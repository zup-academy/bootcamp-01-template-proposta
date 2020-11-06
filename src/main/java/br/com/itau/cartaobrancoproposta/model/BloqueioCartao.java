package br.com.itau.cartaobrancoproposta.model;

public class BloqueioCartao {

    private String resultado;

    @Deprecated
    public BloqueioCartao() {
    }

    public BloqueioCartao(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
