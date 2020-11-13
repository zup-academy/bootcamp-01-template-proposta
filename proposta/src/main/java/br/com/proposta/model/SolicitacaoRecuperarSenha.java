package br.com.proposta.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

//Contagem de Pontos - TOTAL:1
//1 - Cartao

@Entity
public class SolicitacaoRecuperarSenha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String userAgent;
	@NotBlank
	private String ip;
	private LocalDateTime instante = LocalDateTime.now();
	@ManyToOne @Valid
	private Cartao cartao;
	
	
	public SolicitacaoRecuperarSenha(@NotBlank String userAgent, @NotBlank String ip, @Valid Cartao cartao) {
		super();
		this.userAgent = userAgent;
		this.ip = ip;
		this.cartao = cartao;
	}
	
	

	public String getUserAgent() {
		return userAgent;
	}



	public String getIp() {
		return ip;
	}



	public LocalDateTime getInstante() {
		return instante;
	}



	@Override
	public String toString() {
		return "SolicitacaoRecuperarSenha [id=" + id + ", userAgent=" + userAgent + ", ip=" + ip
				+ ", instante=" + instante + "]";
	}
	
	
	
}
