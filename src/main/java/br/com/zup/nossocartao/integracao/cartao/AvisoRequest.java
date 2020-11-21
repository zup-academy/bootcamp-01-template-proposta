package br.com.zup.nossocartao.integracao.cartao;

import br.com.zup.nossocartao.viagem.AvisoViagem;

public class AvisoRequest {

	private String validoAte;

	private String destino;

	@Deprecated
	public AvisoRequest() {
	}

	public AvisoRequest(AvisoViagem avisoViagem) {
		this.validoAte = avisoViagem.getDataTerminoViagem().toString();
		this.destino = avisoViagem.getDestinoViagem();
	}

	public String getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(String validoAte) {
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

}
