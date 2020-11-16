package br.com.zup.nossocartao.viagem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class AvisoViagem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idAvisoViagem;

	@NotBlank
	private String idCartao;

	@NotBlank
	private String destinoViagem;

	@NotNull
	@FutureOrPresent
	private LocalDate dataTerminoViagem;

	private LocalDateTime instanteAviso;

	private String ipCliente;

	private String userAgent;

	@Deprecated
	public AvisoViagem() {
	}

	public AvisoViagem(@NotBlank String idCartao, @NotBlank String destinoViagem,
			@NotNull @FutureOrPresent LocalDate dataTerminoViagem, String ipCliente, String userAgent) {
		this.idCartao = idCartao;
		this.destinoViagem = destinoViagem;
		this.dataTerminoViagem = dataTerminoViagem;
		this.instanteAviso = LocalDateTime.now();
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
	}

	public Long getIdAvisoViagem() {
		return idAvisoViagem;
	}

	public void setIdAvisoViagem(Long idAvisoViagem) {
		this.idAvisoViagem = idAvisoViagem;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}

	public String getDestinoViagem() {
		return destinoViagem;
	}

	public void setDestinoViagem(String destinoViagem) {
		this.destinoViagem = destinoViagem;
	}

	public LocalDate getDataTerminoViagem() {
		return dataTerminoViagem;
	}

	public void setDataTerminoViagem(LocalDate dataTerminoViagem) {
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public LocalDateTime getInstanteAviso() {
		return instanteAviso;
	}

	public void setInstanteAviso(LocalDateTime instanteAviso) {
		this.instanteAviso = instanteAviso;
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
