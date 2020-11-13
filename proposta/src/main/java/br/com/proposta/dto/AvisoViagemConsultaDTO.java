package br.com.proposta.dto;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


//Contagem de Pontos - TOTAL:0

public class AvisoViagemConsultaDTO {

	
	@NotBlank
	private String destino;
	@NotNull @Future @DateTimeFormat (pattern = "yyyy-MM-dd")
	private Date terminoViagem;
	
	
	public AvisoViagemConsultaDTO(@NotBlank String destino, @NotNull @Future Date terminoViagem) {
		super();
		this.destino = destino;
		this.terminoViagem = terminoViagem;
	}


	public String getDestino() {
		return destino;
	}


	public Date getTerminoViagem() {
		return terminoViagem;
	}


	public void setDestino(String destino) {
		this.destino = destino;
	}


	public void setTerminoViagem(Date terminoViagem) {
		this.terminoViagem = terminoViagem;
	}


	@Override
	public String toString() {
		return "AvisoViagemConsultaDTO [destino=" + destino + ", terminoViagem=" + terminoViagem + "]";
	}
	

	
}
