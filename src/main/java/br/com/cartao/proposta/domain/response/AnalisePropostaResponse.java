package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.enums.EstadoAnaliseProposta;

import javax.validation.constraints.NotBlank;

public class AnalisePropostaResponse {

    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotBlank
    private String idProposta;

    private EstadoAnaliseProposta resultadoSolicitacao;

    @Deprecated
    public AnalisePropostaResponse() {
    }

    public AnalisePropostaResponse(@NotBlank String documento, @NotBlank String nome, @NotBlank String idProposta, EstadoAnaliseProposta resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
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

    public EstadoAnaliseProposta getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    @Override
    public String toString() {
        return "AnalisePropostResponse{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", idProposta='" + idProposta + '\'' +
                ", resultadoSolicitacao=" + resultadoSolicitacao +
                '}';
    }
}
