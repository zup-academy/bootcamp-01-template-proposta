package br.com.proposta.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


//Contagem de Pontos - TOTAL:1
//1 - Cartao

@Entity
public class CarteiraPaypal {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull @Email
	private String email;
	@OneToOne @NotNull
	private Cartao cartao;
	
	
	@Deprecated
	public CarteiraPaypal() {
	}
	
	public CarteiraPaypal(@NotNull @Email String email, @NotNull Cartao cartao) {
		super();
		this.email = email;
		this.cartao = cartao;
	}


	public Long getId() {
		return id;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String toString() {
		return "CarteiraPaypal [id=" + id + ", email=" + email + "]";
	}	
	
}
