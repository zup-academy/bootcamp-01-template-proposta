package br.com.proposta.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.proposta.model.AvisoViagem;
import br.com.proposta.model.Cartao;


//Contagem de Pontos - TOTAL:2
//1 - AvisoViagem
//1 - Cartao

public class AvisoViagemDTO {

	@NotBlank
	private String destino;
	@NotNull @Future @DateTimeFormat (pattern = "yyyy-MM-dd")
	private Date terminoViagem;
	
	@Deprecated
	public AvisoViagemDTO() {
	}
	
	public AvisoViagemDTO(@NotBlank String destino, @Future Date terminoViagem) {
		this.destino = destino;
		this.terminoViagem = terminoViagem;
	}

	
	public AvisoViagem toModel(Cartao cartao, String userAgent, String ip) {
		return new AvisoViagem(this.destino, this.terminoViagem, userAgent, ip, cartao);
	}

	public String getDestino() {
		return destino;
	}


	public Date getTerminoViagem() {
		return terminoViagem;
	}

	@Override
	public String toString() {
		return "AvisoViagemDTO [destino=" + destino + ", terminoViagem=" + terminoViagem + "]";
	}
	
}
