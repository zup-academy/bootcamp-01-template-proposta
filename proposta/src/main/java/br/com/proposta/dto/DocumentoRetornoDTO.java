package br.com.proposta.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class DocumentoRetornoDTO {
	
	private String documento;
	private String nome;
	private String resultadoSolicitacao;
	private String idProposta;
	
	
	public DocumentoRetornoDTO(String documento, String nome, String resultadoSolicitacao, String idProposta) {
		super();
		this.documento = documento;
		this.nome = nome;
		this.resultadoSolicitacao = resultadoSolicitacao;
		this.idProposta = idProposta;
	}


	public String getDocumento() {
		return documento;
	}


	public String getNome() {
		return nome;
	}


	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}


	public String getIdProposta() {
		return idProposta;
	}


	public void setDocumento(String documento) {
		this.documento = documento;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public void setResultadoSolicitacao(String resultadoSolicitacao) {
		this.resultadoSolicitacao = resultadoSolicitacao;
	}


	public void setIdProposta(String idProposta) {
		this.idProposta = idProposta;
	}


	@Override
	public String toString() {
		return "DocumentoRetornoDTO [documento=" + documento + ", nome=" + nome + ", resultadoSolicitacao="
				+ resultadoSolicitacao + ", idProposta=" + idProposta + "]";
	}
	
	
}
