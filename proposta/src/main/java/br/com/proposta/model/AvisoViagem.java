package br.com.proposta.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Contagem de Pontos - TOTAL:1
//1 - Cartao

@Entity
public class AvisoViagem {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String destino;
	@NotNull @Future
	private Date terminoViagem;
	@NotBlank
	private String userAgent;
	@NotBlank
	private String ip;
	private LocalDateTime instante = LocalDateTime.now();
	@ManyToOne @Valid
	private Cartao cartao;
	
	@Deprecated
	public AvisoViagem() {
	}

	public AvisoViagem(@NotBlank String destino, @NotNull @Future Date terminoViagem, @NotBlank String userAgent,
			@NotBlank String ip, @Valid Cartao cartao) {
		super();
		this.destino = destino;
		this.terminoViagem = terminoViagem;
		this.userAgent = userAgent;
		this.ip = ip;
		this.cartao = cartao;
	}

	public String getDestino() {
		return destino;
	}


	public Date getTerminoViagem() {
		return terminoViagem;
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

	@JsonIgnore
	public Cartao getCartao() {
		return cartao;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	public void setTerminoViagem(Date terminoViagem) {
		this.terminoViagem = terminoViagem;
	}


	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setInstante(LocalDateTime instante) {
		this.instante = instante;
	}


	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}


	@Override
	public String toString() {
		return "AvisoViagem [id=" + id + ", destino=" + destino + ", terminoViagem=" + terminoViagem + ", userAgent="
				+ userAgent + ", ip=" + ip + ", instante=" + instante + "]";
	}
	
}
