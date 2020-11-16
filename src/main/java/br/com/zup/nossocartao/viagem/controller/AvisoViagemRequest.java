package br.com.zup.nossocartao.viagem.controller;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.zup.nossocartao.viagem.AvisoViagem;

public class AvisoViagemRequest {

	private String idCartao;

	@NotBlank
	private String destinoViagem;

	@NotNull
	@FutureOrPresent
	private LocalDate dataTerminoViagem;

	private String ipCliente;

	private String userAgent;

	@Deprecated
	public AvisoViagemRequest() {
	}

	public AvisoViagemRequest(@NotBlank String destinoViagem, @NotNull @FutureOrPresent LocalDate dataTerminoViagem) {
		this.destinoViagem = destinoViagem;
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public String getIdCartao() {
		return idCartao;
	}

	public void setDestinoViagem(String destinoViagem) {
		this.destinoViagem = destinoViagem;
	}

	public void setDataTerminoViagem(LocalDate dataTerminoViagem) {
		this.dataTerminoViagem = dataTerminoViagem;
	}

	public void setDadosClient(String ipCliente, String userAgent, String idCartao) {
		this.ipCliente = ipCliente;
		this.userAgent = userAgent;
		this.idCartao = idCartao;
	}

	public AvisoViagem gerarDadosViagem() {
		AvisoViagem gerarDadosViagem = new AvisoViagem(idCartao, destinoViagem, dataTerminoViagem, ipCliente,
				userAgent);
		return gerarDadosViagem;
	}
}
