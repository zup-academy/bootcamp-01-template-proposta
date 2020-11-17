package br.com.zup.proposta.controllers.form;

public class AnaliseRequestForm {
    
    private String documento;
    private String nome;
    private String idProposta;

    public AnaliseRequestForm(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getNome() {
        return this.nome;
    }

    public String getIdProposta() {
        return this.idProposta;
    }

}
