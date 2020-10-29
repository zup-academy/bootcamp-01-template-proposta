package br.com.proposta.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.proposta.dto.enums.StatusCartao;

@Entity
public class Cartao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String numero;
	@OneToOne
	private Proposta proposta;
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)    
	private Set<Biometria> biometrias = new HashSet<>();
	@OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)    
	private Set<EstadoCartao> estadoCartao = new HashSet<>();
	
	@Deprecated
	public Cartao() {
		super();
	}

	public Cartao(@NotBlank String numero) {
		super();
		this.numero = numero;
	}


	public String getNumero() {
		return numero;
	}

	@JsonIgnore
	public Proposta getProposta() {
		return proposta;
	}
	
	public Set<EstadoCartao> getEstadoCartao() {
		return estadoCartao;
	}

	public Set<Biometria> getBiometrias() {
		return biometrias;
	}

	public void setProposta(Proposta proposta) {
		this.proposta = proposta;
	}

	public void adicionaBiometria(Biometria biometria) {
		this.biometrias.add(biometria);
	}
	
	public void bloqueia(String userAgent,String ipRemoto) {
		this.estadoCartao.add(new EstadoCartao(StatusCartao.BLOQUEADO,userAgent, ipRemoto,this));
	}

	@Override
	public String toString() {
		return "Cartao [id=" + id + ", numero=" + numero + ", biometrias=" + biometrias + ", estadoCartao="
				+ estadoCartao + "]";
	}


	
}
