package br.com.proposta.transferenciadados.respostas;

public class RespostaNumeroCartao {

    private String id;

    @Deprecated
    public RespostaNumeroCartao(){}

    public RespostaNumeroCartao(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
