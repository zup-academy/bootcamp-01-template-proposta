package br.com.proposta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


//Contagem de Pontos - TOTAL:0

public class AssociarPaypalDTO {

	
	@NotNull @Email
	private String email;

	@Deprecated
	public AssociarPaypalDTO() {
	}
	
	public AssociarPaypalDTO(@NotNull @Email String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "AssociarPaypalDTO [email=" + email + "]";
	}
	
}
