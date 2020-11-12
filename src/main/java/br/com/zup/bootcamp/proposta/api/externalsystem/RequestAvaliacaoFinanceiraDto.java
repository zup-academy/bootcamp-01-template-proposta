package br.com.zup.bootcamp.proposta.api.externalsystem;

public class RequestAvaliacaoFinanceiraDto {

    private String documento;
    private String nome;
    private String idProposta;

    @Deprecated
    public RequestAvaliacaoFinanceiraDto(){}

    public RequestAvaliacaoFinanceiraDto(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
