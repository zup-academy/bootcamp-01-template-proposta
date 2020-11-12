package br.com.itau.cartaobrancoproposta.model;

public class SolicitacaoBloqueioCartao {

    private String resultado;

    @Deprecated
    public SolicitacaoBloqueioCartao() {
    }

    public SolicitacaoBloqueioCartao(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
