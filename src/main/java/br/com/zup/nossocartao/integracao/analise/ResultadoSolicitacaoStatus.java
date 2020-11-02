package br.com.zup.nossocartao.integracao.analise;

import br.com.zup.nossocartao.proposta.StatusSolicitacao;

public enum ResultadoSolicitacaoStatus {

	COM_RESTRICAO(StatusSolicitacao.NAO_ELEGIVEL), SEM_RESTRICAO(StatusSolicitacao.ELEGIVEL);

	private StatusSolicitacao resultado;

	ResultadoSolicitacaoStatus(StatusSolicitacao resultadoStatus) {
		this.resultado = resultadoStatus;
	}

	public StatusSolicitacao getStatusAvaliacao() {
		return this.resultado;
	}

}
