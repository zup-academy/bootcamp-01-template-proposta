package io.github.evertocnsouza.rest.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import io.github.evertocnsouza.domain.entity.Proposta;
import io.github.evertocnsouza.validation.annotation.CpfCnpj;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PropostaSolicitadaRequest {

    @CpfCnpj
    private String documento;

    private String nome;

    private String idProposta;


    @Deprecated
    public PropostaSolicitadaRequest() {}

    public PropostaSolicitadaRequest(Proposta proposta){
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