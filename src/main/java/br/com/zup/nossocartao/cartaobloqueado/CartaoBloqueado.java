package br.com.zup.nossocartao.cartaobloqueado;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CartaoBloqueado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCartaoBloqueado;

	@NotBlank
	private String idCartao;

	private LocalDateTime instanteBloqueio;

	@NotBlank
	private String ipCliente;

	@NotBlank
	private String userAgent;

	@NotNull
	private StatusCartao status;

	@Deprecated
	public CartaoBloqueado() {
	}

	public CartaoBloqueado(@NotBlank String idCartao, @NotBlank String ipCliente, @NotBlank String userAgent) {
		this.idCartao = idCartao;
		this.instanteBloqueio = LocalDateTime.now();
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.status = StatusCartao.LIBERADO;
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

	public StatusCartao getStatus() {
		return status;
	}

	public void setStatus(StatusCartao status) {
		this.status = status;
	}

}
