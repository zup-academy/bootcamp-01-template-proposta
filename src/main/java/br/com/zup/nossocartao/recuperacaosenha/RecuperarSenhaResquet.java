package br.com.zup.nossocartao.recuperacaosenha;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

public class RecuperarSenhaResquet {

	@NotBlank
	private String idCartao;

	private LocalDateTime instante;

	private String ipCliente;

	private String userAgent;

	@Deprecated
	public RecuperarSenhaResquet() {
	}

	public RecuperarSenhaResquet(@NotBlank String idCartao, LocalDateTime instante, String ipCliente,
			String userAgent) {
		this.idCartao = idCartao;
		this.instante = instante;
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

	public LocalDateTime getInstante() {
		return instante;
	}

	public void setInstante(LocalDateTime instante) {
		this.instante = instante;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}
