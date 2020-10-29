package br.com.zup.proposta.integracao;

public enum RespostaStatusAvaliacao {
    COM_RESTRICAO(StatusAvaliacaoProposta.NAO_ELEGIVEL), SEM_RESTRICAO(StatusAvaliacaoProposta.ELEGIVEL);

    private StatusAvaliacaoProposta statusAvaliacao;

    private RespostaStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
        this.statusAvaliacao = statusAvaliacao;
    }

    public StatusAvaliacaoProposta getStatusAvaliacao() {
        return statusAvaliacao;
    }
}
