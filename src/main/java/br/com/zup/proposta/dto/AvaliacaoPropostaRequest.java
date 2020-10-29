package br.com.zup.proposta.dto;

import br.com.zup.proposta.model.Proposta;
import br.com.zup.proposta.model.enums.RespostaStatusAvaliacao;
import br.com.zup.proposta.validations.CpfCnpj;

import java.util.UUID;

public class AvaliacaoPropostaRequest {

    @CpfCnpj
    private String documento;
    private String nome;
    private UUID idProposta;
    private RespostaStatusAvaliacao resultadoSolicitacao;

    @Deprecated
    public AvaliacaoPropostaRequest() {
    }

    public AvaliacaoPropostaRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId();
    }

    public String getDocumento() {
        return documento;
    }

    public RespostaStatusAvaliacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getNome() {
        return nome;
    }

    public UUID getIdProposta() {
        return idProposta;
    }

    @Override
    public String toString() {
        return "AvaliacaoPropostaRequest{" +
                "documento='" + documento + '\'' +
                ", resultadoSolicitacao='" + resultadoSolicitacao + '\'' +
                '}';
    }

}
