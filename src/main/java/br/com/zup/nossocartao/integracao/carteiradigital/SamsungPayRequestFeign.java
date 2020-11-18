package br.com.zup.nossocartao.integracao.carteiradigital;

public class SamsungPayRequestFeign {

	private String email;

	private String carteira;

	@Deprecated
	public SamsungPayRequestFeign() {
	}

	public SamsungPayRequestFeign(String email) {
		this.email = email;
		this.carteira = "SamsungPay";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

}
