package br.com.zup.proposta.controllers.apiResponses;

import br.com.zup.proposta.model.enums.ResultadoAnalise;

public class AnaliseResponse {
    
    private String documento;
    private String nome;
    private ResultadoAnalise resultadoSolicitacao;
    private String idProposta;

    public AnaliseResponse(String documento, String nome, ResultadoAnalise resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return this.documento;
    }

    public String getNome() {
        return this.nome;
    }

    public ResultadoAnalise getResultadoSolicitacao() {
        return this.resultadoSolicitacao;
    }

    public String getIdProposta() {
        return this.idProposta;
    }

	public boolean isElegivel() {
		if (resultadoSolicitacao.equals(ResultadoAnalise.COM_RESTRICAO)) {
            return false;
        } else if (resultadoSolicitacao.equals(ResultadoAnalise.SEM_RESTRICAO)) {
            return true;
        }

        throw new RuntimeException("AnaliseResponse recebida incorretamente.");
	}

    @Override
    public String toString() {
        return "{" +
            " documento='" + getDocumento() + "'" +
            ", nome='" + getNome() + "'" +
            ", resultadoSolicitacao='" + getResultadoSolicitacao() + "'" +
            ", idProposta='" + getIdProposta() + "'" +
            "}";
    }

}
