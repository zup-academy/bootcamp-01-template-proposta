package br.com.zup.proposta.dto.request;

import br.com.zup.proposta.annotations.CpfCnpj;
import br.com.zup.proposta.model.Proposta;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SolicitacaoAnaliseRequest {

    @CpfCnpj
    private String documento;

    private String nome;

    private UUID idProposta;

    @Deprecated
    public SolicitacaoAnaliseRequest() {
    }

    public SolicitacaoAnaliseRequest(String documento, String nome, UUID idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.descriptografarDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }
}
