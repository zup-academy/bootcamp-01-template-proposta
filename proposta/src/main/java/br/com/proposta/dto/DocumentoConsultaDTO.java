package br.com.proposta.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.proposta.model.Proposta;
import br.com.proposta.validator.DocumentoValido;

//Contagem de Pontos - TOTAL:1
//1 - Proposta

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class DocumentoConsultaDTO {

	@DocumentoValido
	private String documento;
	private String nome;
	
	@Deprecated
	public DocumentoConsultaDTO() {
	}
	
	public DocumentoConsultaDTO(Proposta proposta) {
		this.documento = proposta.getDocumento();
		this.nome = proposta.getNome();
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

	@Override
	public String toString() {
		return "DocumentoConsultaDTO [documento=" + documento + ", nome=" + nome + "]";
	}
	
	
	

}
