package br.com.zup.nossocartao.carteiradigital;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class SamsungPay {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String carteira;

	@NotBlank
	private String idCartao;

	@Deprecated
	public SamsungPay() {
	}

	public SamsungPay(@NotBlank @Email String email, @NotBlank String idCartao) {
		this.email = email;
		this.carteira = "SAMSUNG PAY";
		this.idCartao = idCartao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

}
