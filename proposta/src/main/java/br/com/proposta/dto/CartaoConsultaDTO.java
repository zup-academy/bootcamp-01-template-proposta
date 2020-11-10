package br.com.proposta.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.proposta.model.Proposta;
import br.com.proposta.validator.DocumentoValido;

//Contagem de Pontos - TOTAL:1
//1 - Proposta

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CartaoConsultaDTO {

	@DocumentoValido
	private String documento;
	private String nome;
	private String idProposta;
	
	@Deprecated
	public CartaoConsultaDTO() {
	}
	
	public CartaoConsultaDTO(Proposta proposta) {
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
		this.idProposta = proposta.getId().toString();
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

	@Override
	public String toString() {
		return "CartaoConsultaDTO [documento=" + documento + ", nome=" + nome + ", idProposta=" + idProposta + "]";
	}

}