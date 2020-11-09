package br.com.cartao.proposta.domain.request;

/**
 * Carga intrínseca máxima permitida - 9
 * Carga intrínseca da classe - 0
 */

public class SolicitacaoBloqueioRequest {

    private String sistemaResponsavel;

    public SolicitacaoBloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    public void setSistemaResponsavel(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    @Override
    public String toString() {
        return "SolicitacaoBloqueioRequest{" +
                "sistemaResponsavel='" + sistemaResponsavel + '\'' +
                '}';
    }
}
