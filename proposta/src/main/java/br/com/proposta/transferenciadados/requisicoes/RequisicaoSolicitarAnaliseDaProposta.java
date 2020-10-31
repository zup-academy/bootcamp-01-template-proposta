package br.com.proposta.transferenciadados.requisicoes;

import br.com.proposta.entidades.Proposta;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RequisicaoSolicitarAnaliseDaProposta {


    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private String idProposta;


    @Deprecated
    public RequisicaoSolicitarAnaliseDaProposta() {}

    public RequisicaoSolicitarAnaliseDaProposta(Proposta proposta){
        this.documento = proposta.getIdentificacao();
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
