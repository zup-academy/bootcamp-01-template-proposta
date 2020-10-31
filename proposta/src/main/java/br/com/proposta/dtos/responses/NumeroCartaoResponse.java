package br.com.proposta.dtos.responses;

public class NumeroCartaoResponse {

    private String id;

    @Deprecated
    public NumeroCartaoResponse(){}

    public NumeroCartaoResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
