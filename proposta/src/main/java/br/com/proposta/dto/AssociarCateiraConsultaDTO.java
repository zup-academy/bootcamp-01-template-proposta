package br.com.proposta.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


//Contagem de Pontos - TOTAL:0


public class AssociarCateiraConsultaDTO {
	
	@NotNull @Email
	private String email;
	@NotNull
	private String carteira;
	
	
	public AssociarCateiraConsultaDTO(@NotNull @Email String email, @NotNull String carteira) {
		this.email = email;
		this.carteira = carteira;
	}


	public String getEmail() {
		return email;
	}


	public String getCarteira() {
		return carteira;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}


	@Override
	public String toString() {
		return "AssociarCateiraConsultaDTO [email=" + email + ", carteira=" + carteira + "]";
	}
	
	
}
