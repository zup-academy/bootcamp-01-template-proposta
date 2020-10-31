package br.com.proposta.transferenciadados.respostas;

public class RespostaAvisoViagem {

    private String resultado;

    @Deprecated
    public RespostaAvisoViagem(){}

    public RespostaAvisoViagem(String resultado) {
        this.resultado = resultado;
    }


    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
