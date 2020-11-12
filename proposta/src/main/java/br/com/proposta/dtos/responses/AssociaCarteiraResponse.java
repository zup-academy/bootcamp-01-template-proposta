package br.com.proposta.dtos.responses;


public class AssociaCarteiraResponse {

    private String resultado;

    private String id;

    @Deprecated
    private AssociaCarteiraResponse(){}

    public AssociaCarteiraResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
