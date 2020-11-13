package br.com.zup.nossocartao.integracao.cartao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.zup.nossocartao.cartaobloqueado.StatusCartao;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class CartaoBloqueadoResponse {

	private StatusCartao resultado;

	public StatusCartao getResultado() {
		return resultado;
	}

	public void setResultado(StatusCartao resultado) {
		this.resultado = resultado;
	}

}
