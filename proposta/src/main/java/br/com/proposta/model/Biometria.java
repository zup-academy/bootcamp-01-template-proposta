package br.com.proposta.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

//Contagem de Pontos - TOTAL:1
//1 - Cartao

@Entity
public class Biometria {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private byte[] digital;
	private LocalDateTime instante = LocalDateTime.now();
	@ManyToOne @NotNull
	Cartao cartao;

	@Deprecated
	public Biometria() {

	}
	
	public Biometria(String digital) {
		byte[] digitalBase64 = Base64.getEncoder().encode(digital.getBytes());
		this.digital = digitalBase64;
	}
	
	public Long getId() {
		return id;
	}

	public byte[] getDigital() {
		return digital;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}

	@Override
	public String toString() {
		return "Biometria [id=" + id + ", digital=" + Arrays.toString(digital) + ", instante=" + instante + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(digital);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Biometria other = (Biometria) obj;
		if (!Arrays.equals(digital, other.digital))
			return false;
		return true;
	}	
	
}
