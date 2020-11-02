package br.com.zup.nossocartao.integracao;

public class SolicitacaoAnaliseResponse {

	private String documento;

	private String nome;

	private String idProposta;

	private ResultadoSolicitacaoStatus resultadoSolicitacao;

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

	public ResultadoSolicitacaoStatus getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}

	public void setResultadoSolicitacao(ResultadoSolicitacaoStatus resultadoSolicitacao) {
		this.resultadoSolicitacao = resultadoSolicitacao;
	}

}
