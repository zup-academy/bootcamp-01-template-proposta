package br.com.zup.proposta.enums;

public enum RespostaStatusAvaliacao {
    SEM_RESTRICAO(StatusAvaliacaoProposta.ELEGIVEL), COM_RESTRICAO(StatusAvaliacaoProposta.NAO_ELEGIVEL);

    private StatusAvaliacaoProposta statusProposta;

    private RespostaStatusAvaliacao(StatusAvaliacaoProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public StatusAvaliacaoProposta getStatusProposta() {
        return statusProposta;
    }
}
