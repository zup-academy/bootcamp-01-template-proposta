package br.com.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.proposta.dto.enums.StatusCartao;

@Entity
public class EstadoCartao {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private StatusCartao statusCartao;
	@NotBlank
	private String userAgent;
	@NotBlank
	private String ipRemoto;
	private LocalDateTime instante = LocalDateTime.now();
	@ManyToOne @Valid
	private Cartao cartao;
	
	@Deprecated
	public EstadoCartao() {
	}
	
	public EstadoCartao(@NotNull StatusCartao statusCartao, @NotBlank String userAgent, @NotBlank String ipRemoto,
			@Valid Cartao cartao) {
		super();
		this.statusCartao = statusCartao;
		this.userAgent = userAgent;
		this.ipRemoto = ipRemoto;
		this.cartao = cartao;
	}
	
	public boolean verificaStatus(StatusCartao possivelStatusUso) {
		return this.statusCartao.equals(possivelStatusUso);
	}

	public StatusCartao getStatusCartao() {
		return statusCartao;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public String getIpRemoto() {
		return ipRemoto;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "EstadoCartao [id=" + id + ", statusCartao=" + statusCartao + ", userAgent=" + userAgent + ", ipRemoto="
				+ ipRemoto + ", instante=" + instante + "]";
	}
	
	
	

}
