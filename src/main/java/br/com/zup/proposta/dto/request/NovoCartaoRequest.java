package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.model.Proposta;

public class NovoCartaoRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public NovoCartaoRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = String.valueOf(proposta.getId());
    }


    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}
