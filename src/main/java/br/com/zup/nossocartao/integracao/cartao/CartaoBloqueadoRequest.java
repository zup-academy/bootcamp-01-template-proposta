package br.com.zup.nossocartao.integracao.cartao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CartaoBloqueadoRequest {

	private String sistemaResponsavel;

	public CartaoBloqueadoRequest() {
		this.sistemaResponsavel = "ITAU";
	}

	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}

	public void setSistemaResponsavel(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}

}
