package br.com.zup.nossocartao.integracao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.zup.nossocartao.proposta.Proposta;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class SolicitacaoAnaliseRequest {

	private String documento;

	private String nome;

	private String idProposta;

	@Deprecated
	public SolicitacaoAnaliseRequest() {
	}

	public SolicitacaoAnaliseRequest(Proposta dadosSolicitacao) {
		this.documento = dadosSolicitacao.getCpfCnpj();
		this.nome = dadosSolicitacao.getNome();
		this.idProposta = dadosSolicitacao.getId().toString();
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
