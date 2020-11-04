package br.com.cartao.proposta.domain.enums;

public class SolicitacaoBloqueioIntegracaoResponse {

    private EstadoBloqueioCartaoIntegracao resultado;

    @Deprecated
    public SolicitacaoBloqueioIntegracaoResponse() {
    }

    public SolicitacaoBloqueioIntegracaoResponse(EstadoBloqueioCartaoIntegracao resultado) {
        this.resultado = resultado;
    }

    public EstadoBloqueioCartaoIntegracao getResultado() {
        return resultado;
    }

    public void setResultado(EstadoBloqueioCartaoIntegracao resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "SolicitacaoBloqueioIntegracaoResponse{" +
                "resultado=" + resultado +
                '}';
    }
}
