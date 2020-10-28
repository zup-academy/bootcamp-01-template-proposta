package br.com.cartao.proposta.domain.response;

import br.com.cartao.proposta.domain.enums.EstadoAnaliseProposta;

import javax.validation.constraints.NotBlank;

public class AnalisePropostResponse {

    @NotBlank
    private final String documento;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String idProposta;

    private final EstadoAnaliseProposta resultadoSolicitacao;

    public AnalisePropostResponse(@NotBlank String documento, @NotBlank String nome, @NotBlank String idProposta, EstadoAnaliseProposta resultadoSolicitacao) {
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
