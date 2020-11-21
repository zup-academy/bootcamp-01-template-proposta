package br.com.zup.nossocartao.carteiradigital;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Paypal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	private String idCartao;

	@Email
	@NotBlank
	private String email;

	private String carteira;

	@Deprecated
	public Paypal() {
	}

	public Paypal(@NotBlank String idCartao, @Email @NotBlank String email) {
		this.idCartao = idCartao;
		this.email = email;
		this.carteira = "PAYPAL";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
