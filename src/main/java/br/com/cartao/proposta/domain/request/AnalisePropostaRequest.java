package br.com.cartao.proposta.domain.request;

import javax.validation.constraints.NotBlank;

public class AnalisePropostaRequest {

    @NotBlank
    private final String documento;
    @NotBlank
    private final String nome;
    @NotBlank
    private final String idProposta;

    public AnalisePropostaRequest(@NotBlank String documento,@NotBlank String nome,@NotBlank String idProposta) {
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

    @Override
    public String toString() {
        return "AnalisePropostaRequest{" +
                "documento='" + documento + '\'' +
                ", nome='" + nome + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
