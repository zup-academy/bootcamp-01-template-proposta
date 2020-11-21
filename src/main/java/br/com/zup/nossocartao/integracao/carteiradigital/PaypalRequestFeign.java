package br.com.zup.nossocartao.integracao.carteiradigital;

import javax.validation.constraints.NotBlank;

public class PaypalRequestFeign {

	private String email;

	private String carteira;

	@Deprecated
	public PaypalRequestFeign() {
	}

	public PaypalRequestFeign(@NotBlank String email) {
		this.email = email;
		this.carteira = "PAYPAL";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getEmail() {
		return email;
	}

	public String getCarteira() {
		return carteira;
	}

}
