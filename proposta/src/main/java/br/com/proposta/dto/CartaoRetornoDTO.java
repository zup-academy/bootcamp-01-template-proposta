package br.com.proposta.dto;

import javax.validation.constraints.NotBlank;

import br.com.proposta.model.Cartao;

//Contagem de Pontos - TOTAL:1
//1 - Cartao


public class CartaoRetornoDTO {

	@NotBlank
	private String id;
	private String emitidoEm;
	private String idProposta;
	
	@Deprecated
	public CartaoRetornoDTO() {
	}
	
	public CartaoRetornoDTO(@NotBlank String id, String emitidoEm, String idProposta) {
		super();
		this.id = id;
		this.emitidoEm = emitidoEm;
		this.idProposta = idProposta;
	}

	public Cartao toModel() {
		return new Cartao(this.id);
	}

	public String getId() {
		return id;
	}


	public String getEmitidoEm() {
		return emitidoEm;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setEmitidoEm(String emitidoEm) {
		this.emitidoEm = emitidoEm;
	}


	public String getIdProposta() {
		return idProposta;
	}

	public void setIdProposta(String idProposta) {
		this.idProposta = idProposta;
	}

	@Override
	public String toString() {
		return "CartaoRetornoDTO [id=" + id + ", emitidoEm=" + emitidoEm + ", idProposta=" + idProposta + "]";
	}

}
