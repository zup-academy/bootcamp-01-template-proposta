package br.com.zup.nossocartao.integracao.cartao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.zup.nossocartao.proposta.Proposta;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CartaoRequest {

	private String documento;

	private String nome;

	private String idProposta;

	@Deprecated
	public CartaoRequest() {
	}

	public CartaoRequest(Proposta propostaCartao) {
		this.documento = propostaCartao.getCpfCnpj();
		this.nome = propostaCartao.getNome();
		this.idProposta = propostaCartao.getId().toString();
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
