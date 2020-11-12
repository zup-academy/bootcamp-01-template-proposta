package br.com.zup.nossocartao.cartao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartaoBloqueado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCartaoBloqueado;

	private String idCartao;

	private LocalDateTime instanteBloqueio;

	private String ipCliente;

	private String userAgent;

	@Deprecated
	public CartaoBloqueado() {
	}

	public CartaoBloqueado(String idCartao, String ipCliente, String userAgent) {
		this.idCartao = idCartao;
		this.instanteBloqueio = LocalDateTime.now();
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}

	public Long getIdCartaoBloqueado() {
		return idCartaoBloqueado;
	}

	public void setIdCartaoBloqueado(Long idCartaoBloqueado) {
		this.idCartaoBloqueado = idCartaoBloqueado;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

	public LocalDateTime getInstanteBloqueio() {
		return instanteBloqueio;
	}

	public void setInstanteBloqueio(LocalDateTime instanteBloqueio) {
		this.instanteBloqueio = instanteBloqueio;
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
